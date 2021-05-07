package methods.dimensional.poly;

public class GradientDescendMethod extends AbstractGradientMethod {

    public GradientDescendMethod(final QuadraticForm form, final double eps) {
        super(form, eps);
    }

    public GradientDescendMethod(final QuadraticForm form) {
        super(form);
    }

    public GradientDescendMethod() {
        super();
    }

    public double[] findMin() {
        final int n = form.getN();
        // step 1
        double alpha = 2d / (form.getMaxValue() + form.getMinValue());

        DoubleVector x = new DoubleVector(n);
        DoubleVector y;
        double f_x = form.apply(x);
        table.add(new State(x, f_x));
        double f_y;

        while (true) {
            // step 2
            final DoubleVector gradient = form.gradient(x);
            final double norm = gradient.norm();
            if (norm <= eps) {
                break;
            }
            int iter = 0;
            while (iter++ < 1000) {
                // step 3
                final DoubleVector alphaX = gradient.multiplyBy(alpha);
                y = x.subtract(alphaX);
                f_y = form.apply(y);

                if (f_y <= f_x) {
                    x = y;
                    f_x = f_y;
                    table.add(new State(x, f_x));
                    break;
                } else {
                    // step 4
                    alpha /= 2d;
                }
            }
        }

        return x.toArray();
    }

    @Override
    public String getName() {
        return "Метод градиентного спуска";
    }
}
