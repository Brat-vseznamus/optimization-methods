package functions;

import java.util.function.UnaryOperator;

import functions.oneDimensionOptimisation.functions.BrentsMethod;
import functions.oneDimensionOptimisation.functions.OptimizationAlgorithm;

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
        DoubleVector y = new DoubleVector(n); 
        double f_x = form.apply(x);
        double f_y = 0;

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
            final OptimizationAlgorithm brent = new BrentsMethod(function);
            final double alpha = brent.findMin(a, b);
            final DoubleVector alphaX = gradient.multiplyBy(alpha);
            y = x.subtract(alphaX);
            f_y = form.apply(y);
            x = y;
            f_x = f_y;
        }
        return x.stream().mapToDouble(v -> v).toArray();
    }
}
