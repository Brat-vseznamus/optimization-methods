package methods.dimensional.poly;

public class GradientDescendMethod extends AbstractGradientMethod {

    public GradientDescendMethod(final QuadraticForm form, final double eps) {
        super(form, eps);
    }

    public GradientDescendMethod(final QuadraticForm form) {
        super(form);
    }

    public double[] findMin() {
        final int n = form.getN();
        // step 1
        double alpha = 2d / (form.getMaxValue() + form.getMinValue());

        DoubleVector x = new DoubleVector(n);
        DoubleVector y;
        double f_x = form.apply(x);
        // System.out.println("f_x = " + f_x);
        table.add(new State(x, f_x));
        double f_y;

        while (true) {
            // step 2
            final DoubleVector gradient = form.gradient(x);
            final double norm = gradient.norm();
            // System.out.println(gradient);
            if (norm < eps) {
                break;
            }

            while (true) {
                // step 3
                final DoubleVector alphaX = gradient.multiplyBy(alpha);
                // System.out.println("alpha * gradient = " + alphaX);
                y = x.subtract(alphaX);
                // System.out.println("x = " + x);
                // System.out.println("y = " + y);
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
                //     Thread.sleep(2000);
                // } catch (Exception e) {
                //     //TODO: handle exception
                // }   
            }
        }

        return x.stream().mapToDouble(v -> v).toArray();
    }
}
