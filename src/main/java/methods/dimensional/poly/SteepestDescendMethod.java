package methods.dimensional.poly;

import java.util.function.UnaryOperator;

import matrix.DoubleVector;
import methods.dimensional.one.OneDimensionalOptimizationMethod;
import methods.dimensional.one.ParabolicMethod;

public class SteepestDescendMethod extends AbstractGradientMethod {
    final OneDimensionalOptimizationMethod method;

    public SteepestDescendMethod(final OneDimensionalOptimizationMethod method,
                                 final QuadraticForm form, final double eps) {
        super(form, eps);
        this.method = method;
    }

    public SteepestDescendMethod(final OneDimensionalOptimizationMethod method,
                                 final QuadraticForm form) {
        this(method, form, DEFAULT_EPS);
    }

    public SteepestDescendMethod(final QuadraticForm form) {
        this(new ParabolicMethod(), form, DEFAULT_EPS);
    }

    public SteepestDescendMethod(final OneDimensionalOptimizationMethod method) {
        this(method, null);
    }

    public SteepestDescendMethod() {
        this(null, null);
    }

    @Override
    public double[] findMin() {
        final int n = form.getN();
        final double a = 0d;
        final double b = 2d / form.getMinValue();

        // step 1
        DoubleVector x = new DoubleVector(n);
        double f_x = form.apply(x);

        if (iterationsWithoutTable) {
            iterations++;
        } else {
            table.add(new State(x, f_x));
        }

        while (true) {
            // step 2
            final DoubleVector gradient = form.gradient(x);
            final double norm = gradient.norm();
            if (norm <= eps) {
                break;
            }

            // step 3
            final DoubleVector xc = x;
            final UnaryOperator<Double> function = (arg) -> {
                DoubleVector vc = xc;
                vc = vc.subtract(gradient.multiplyBy(arg));
                return form.apply(vc);
            };
            method.setFunction(function);
;
            final double alpha = method.findMin(a, b);
            final DoubleVector alphaX = gradient.multiplyBy(alpha);
            x = x.subtract(alphaX);
            f_x = form.apply(x);
            if (iterationsWithoutTable) {
                iterations++;
            } else {
                table.add(new State(x, f_x));
            }
        }
        return x.toArray();
    }

    @Override
    public String getName() {
        return "Метод наискорейшего спуска";
    }
}
