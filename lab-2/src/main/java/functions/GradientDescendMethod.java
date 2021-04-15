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

    public double[] findMin() {
        int n = form.getN();
        // step 1
        double alpha = 2d / (form.getMaxValue() + form.getMinValue());
        double[] x = new double[n];
        double[] y = new double[n];
        double f_x = form.apply(x);
        double f_y = 0;

        while (true) {
            // step 2
            double[] gradient = form.gradient(x);
            double norm = Math.sqrt(Arrays.stream(gradient).map(v -> v * v).sum());
            if (norm < eps) {
                break;
            }

            while (true) {
                System.out.printf("x = %s\n%n", toStr(x));
                System.out.printf("f(x) = %f\n%n", f_x);
                System.out.printf("y = %s\n%n", toStr(y));
                System.out.printf("f(y) = %f\n%n", f_y);
                System.out.printf("grad = %s\n%n", toStr(gradient));
                System.out.printf("alpha = %f\n%n", alpha);
                System.out.println("###############################");
                // step 3
                for (int i = 0; i < n; ++i) {
                    y[i] = x[i] - alpha * gradient[i];
                }
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
