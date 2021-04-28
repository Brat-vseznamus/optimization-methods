package methods.dimensional.poly;

public class ConjugateGradientMethod extends AbstractGradientMethod {

    public ConjugateGradientMethod(final QuadraticForm form, final double eps) {
        super(form, eps);
    }

    public ConjugateGradientMethod(final QuadraticForm form) {
        super(form);
    }


    @Override
    public double[] findMin() {
        final int n = form.getN();
        DoubleVector x = new DoubleVector(n);
        table.add(new State(x, form.apply(x)));
        DoubleVector p = form.gradient(x).multiplyBy(-1);
        DoubleVector xNext = x;
        do {
            final DoubleVector[] result = iteration(xNext, p);
            x = result[0];
            table.add(new State(x, form.apply(x)));
            xNext = result[1];
            p = result[2];
            // System.out.printf("%s, %s, %s%n", x.toString(), xNext.toString(), p.toString());
        } while (xNext.subtract(x).norm() >= eps);
        return x.stream().mapToDouble(v -> v).toArray();
    }

    private DoubleVector[] iteration(DoubleVector xNext,
                                     DoubleVector p) {
        final DoubleVector x = xNext;
        DoubleVector gradient = form.gradient(x);
        final double denominator = (form.getA().multiply(p)).scalar(p);
        // count alpha_k
        final double alpha = -(gradient.scalar(p)) / denominator;
        // update x_k+1
        xNext = x.add(p.multiplyBy(alpha));
        // count beta_k
        gradient = form.gradient(xNext);
        final double beta = (form.getA().multiply(gradient)).scalar(p) / denominator;
        // update p_k+1
        p = (p.multiplyBy(beta)).subtract(gradient);
        return new DoubleVector[]{x, xNext, p};
    }

}
