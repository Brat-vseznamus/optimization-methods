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

    private String toStr(DoubleVector point) {
        return point.toString();
    }

    public double[] findMin() {
        int n = form.getN();
        // step 1
        double alpha = 2d / (form.getMaxValue() + form.getMinValue());
        // double[] x = new double[n];
        // double[] y = new double[n];
        DoubleVector x = new DoubleVector(n);
        DoubleVector y = new DoubleVector(n); 
        double f_x = form.apply(x);
        double f_y = 0;

        while (true) {
            // step 2
            DoubleVector gradient = form.gradient(x);
            double norm = gradient.norm();
            if (norm < eps) {
                break;
            }

            while (true) {
                System.out.println(String.format("x = %s\n", toStr(x)));
                System.out.println(String.format("f(x) = %f\n", f_x));
                System.out.println(String.format("y = %s\n", toStr(y)));
                System.out.println(String.format("f(y) = %f\n", f_y));
                System.out.println(String.format("grad = %s\n", toStr(gradient)));
                System.out.println(String.format("alpha = %f\n", alpha));
                System.out.println("###############################");
                // step 3
                DoubleVector alphaX = gradient.multiplyBy(-alpha);
                y = y.add(alphaX);
                // for (int i = 0; i < n; ++i) {
                //     y[i] = x[i] - alpha * gradient[i];
                // }
                f_y = form.apply(y);

                if (f_y < f_x) {
                    x = y;
                    f_x = f_y;
                    break;
                } else {
                    // step 4
                    alpha /= 2;
                }
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

        return x.stream().mapToDouble(v -> v).toArray();
    }
}
