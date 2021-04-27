package functions;

import java.util.Arrays;

public class SteepestDescendMethod extends AbstractGradientMethod {

    public SteepestDescendMethod(QuadraticForm form, double eps) {
        super(form, eps);
    }

    public SteepestDescendMethod(QuadraticForm form) {
        super(form);
    }

    public double[] findMin() {
        int n = form.getN();
        // step 1
        DoubleVector x = new DoubleVector(n);
        DoubleVector y = new DoubleVector(n); 
        double f_x = form.apply(x);
        double f_y;

        while (true) {
            // step 2
            DoubleVector gradient = form.gradient(x);
            double norm = gradient.norm();
            if (norm < eps) {
                break;
            }

            // step 3
            double a = 0d, b = 2d / form.getMaxValue();
            // UnaryOperator<Double> function = (arg) -> {
                // double[] vector = new double[n];
                // for (int i = 0; i < n; ++i) {
                    // vector[i] = vector[i] - arg * gradient[i];
                // }
                // return f.apply(vector);
            // };
            // OptimizationAlgorithm brent = new BrentsMethod(function);
            // double alpha = brent.findMin(a, b);
            
            // for (int i = 0; i < n; ++i) {
                // x[i] = x[i] - alpha * gradient[i];
            // }
        }

        return x.stream().mapToDouble(v -> v).toArray();
    }
}
