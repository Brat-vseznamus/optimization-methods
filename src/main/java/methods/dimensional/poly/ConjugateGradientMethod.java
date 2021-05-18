package methods.dimensional.poly;

public class ConjugateGradientMethod extends AbstractGradientMethod {

    public ConjugateGradientMethod(final QuadraticForm form, final double eps) {
        super(form, eps);
    }

    public ConjugateGradientMethod(final QuadraticForm form) {
        super(form);
    }

    public ConjugateGradientMethod() {
        super();
    }

    @Override
    public double[] findMin() {
        final int n = form.getN();
        DoubleVector x = new DoubleVector(n);
        DoubleVector p = form.gradient(x).multiplyBy(-1);
        DoubleVector xNext = x;
        do {
            final DoubleVector[] result = iteration(xNext, p);
            x = result[0];
            xNext = result[1];
            p = result[2];
            if (iterationsWithoutTable) {
                iterations++;
            } else {
                table.add(new State(x, form.apply(x)));
            }
        } while (xNext.subtract(x).norm() >= eps);
        return x.toArray();
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

    @Override
    public String getName() {
        return "Метод сопряженных градиентов";
    }

}
