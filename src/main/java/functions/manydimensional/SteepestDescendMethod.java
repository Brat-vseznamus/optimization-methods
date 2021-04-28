package functions.manydimensional;

import java.util.function.UnaryOperator;

import functions.onedimensional.BrentsMethod;
import functions.onedimensional.OptimizationAlgorithm;

public class SteepestDescendMethod extends AbstractGradientMethod {

    public SteepestDescendMethod(final QuadraticForm form, final double eps) {
        super(form, eps);
    }

    public SteepestDescendMethod(final QuadraticForm form) {
        super(form);
    }

    @Override
    public double[] findMin() {
        final int n = form.getN();
        // step 1
        DoubleVector x = new DoubleVector(n);
        double f_x = form.apply(x);
        table.add(new State(x, f_x));

        while (true) {
            // step 2
            final DoubleVector gradient = form.gradient(x);
            final double norm = gradient.norm();
            if (norm < eps) {
                break;
            }
            // step 3
            final double a = 0d;
            final double b = 2d / form.getMaxValue();
            final DoubleVector xc = x;
            final UnaryOperator<Double> function = (arg) -> {
                DoubleVector vc = xc;
                vc = vc.subtract(gradient.multiplyBy(arg));
                return form.apply(vc);
            };
            // TODO replace with custom method
            final OptimizationAlgorithm brent = new BrentsMethod(function);
            final double alpha = brent.findMin(a, b);
            final DoubleVector alphaX = gradient.multiplyBy(alpha);
            x = x.subtract(alphaX);
            f_x = form.apply(x);
            table.add(new State(x, f_x));
        }
        return x.stream().mapToDouble(v -> v).toArray();
    }
}
