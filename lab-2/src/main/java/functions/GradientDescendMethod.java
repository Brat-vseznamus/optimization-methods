package functions;

import java.util.Arrays;
import java.util.stream.Collectors;

public class GradientDescendMethod extends AbstractGradientMethod {

    public GradientDescendMethod(QuadraticForm form, double eps) {
        super(form, eps);
    }

    public GradientDescendMethod(QuadraticForm form) {
        super(form);
    }

    private String toStr(double[] point) {
        return Arrays.deepToString(
                Arrays.stream(point).boxed().collect(Collectors.toList()).toArray(new Double[1]));
    }

    public DoubleVector findMin() {
        int n = form.getN();
        // step 1
        double alpha = 2d / (form.getMaxValue() + form.getMinValue());
        DoubleVector x = new DoubleVector(n);
        DoubleVector y = new DoubleVector(n);
        double f_x = form.apply(x);
        double f_y = 0;

        while (true) {
            // step 2
            DoubleVector gradient = form.gradient(x);
            if (gradient.norm() < eps) {
                break;
            }

            while (true) {
                System.out.printf("x = %s\n%n", x);
                System.out.printf("f(x) = %f\n%n", f_x);
                System.out.printf("y = %s\n%n", y);
                System.out.printf("f(y) = %f\n%n", f_y);
                System.out.printf("grad = %s\n%n", gradient);
                System.out.printf("alpha = %f\n%n", alpha);
                System.out.println("###############################");
                // step 3
                y = x.subtract(gradient.multiplyBy(alpha));
                f_y = form.apply(y);

                if (f_y < f_x) {
                    x = y;
                    f_x = f_y;
                    break;
                } else {
                    // step 4
                    alpha /= 2;
                }
            }
        }

        return x;
    }
}
