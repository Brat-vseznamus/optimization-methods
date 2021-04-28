package functions;

public class GradientDescendMethod extends AbstractGradientMethod {

    public GradientDescendMethod(final QuadraticForm form, final double eps) {
        super(form, eps);
    }

    public GradientDescendMethod(final QuadraticForm form) {
        super(form);
    }

    private String toStr(final DoubleVector point) {
        return point.toString();
    }

    public double[] findMin() {
        final int n = form.getN();
        // step 1
        double alpha = 2d / (form.getMaxValue() + form.getMinValue());
        // double alpha = 100d;
        
        // double[] x = new double[n];
        // double[] y = new double[n];
        DoubleVector x = new DoubleVector(n);
        DoubleVector y = new DoubleVector(n); 
        double f_x = form.apply(x);
        table.add(new State(x, f_x));
        double f_y = 0;

        while (true) {
            // step 2
            final DoubleVector gradient = form.gradient(x);
            final double norm = gradient.norm();
            if (norm < eps) {
                break;
            }

            while (true) {
                // step 3
                final DoubleVector alphaX = gradient.multiplyBy(alpha);
                y = x.subtract(alphaX);
                f_y = form.apply(y);

                if (f_y < f_x) {
                    x = y;
                    f_x = f_y;
                    table.add(new State(x, f_x));
                    break;
                } else {
                    // step 4
                    alpha /= 2d;
                }
                // try {
                    // Thread.sleep(200);
                // } catch (final InterruptedException e) {
                    // TODO Auto-generated catch block
                    // e.printStackTrace();
                // }
            }
        }

        return x.stream().mapToDouble(v -> v).toArray();
    }
}
