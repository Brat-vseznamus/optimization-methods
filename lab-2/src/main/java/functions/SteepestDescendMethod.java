package functions;

import java.util.function.UnaryOperator;

public class SteepestDescendMethod extends AbstractGradientMethod {

    public SteepestDescendMethod(final QuadraticForm form, final double eps) {
        super(form, eps);
    }

    public SteepestDescendMethod(final QuadraticForm form) {
        super(form);
    }

    public DoubleVector findMin() {
        final int n = form.getN();
        // step 1
        DoubleVector x = new DoubleVector(n);

        while (true) {
            // step 2
            final DoubleVector gradient = form.gradient(x);
//            System.out.printf("x = %s%n%n", x);
//            System.out.printf("f(x) = %f%n%n", form.apply(x));
//            System.out.printf("grad = %s%n%n", gradient);
            if (gradient.norm() < eps) {
                break;
            }

            // step 3
            final double a = 0d;
            final double b = 2d / form.getMaxValue();
            final DoubleVector xCopy = x;
            final UnaryOperator<Double> function = (arg)
                    -> form.apply(xCopy.subtract(gradient.multiplyBy(arg)));
            final OptimizationAlgorithm brent = new BrentsMethod(function);
            final double alpha = brent.findMin(a, b);

//            System.out.printf("alpha = %f%n%n", alpha);
//            System.out.println("###############################");

            x = x.subtract(gradient.multiplyBy(alpha));
        }

        return x;
    }
}
