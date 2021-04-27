package functions;

public class ConjugateGradientMethod extends AbstractGradientMethod {

    public ConjugateGradientMethod(final QuadraticForm form, final double eps) {
        super(form, eps);
    }

    public ConjugateGradientMethod(final QuadraticForm form) {
        super(form);
    }


    Matrix a = form.getA();

    @Override
    public double[] findMin() {
        final int n = form.getN();
        DoubleVector x = new DoubleVector(n);
        DoubleVector p = form.gradient(x).multiplyBy(-1);
        DoubleVector xNext = x;
        do {
            final DoubleVector[] result = iteration(x, xNext, p);
            x = result[0];
            xNext = result[1];
            p = result[2];
        
        } while (xNext.subtract(x).norm() >= eps);
        return x.stream().mapToDouble(v -> v).toArray();
    }

    private DoubleVector[] iteration(DoubleVector x,
                            DoubleVector xNext,
                            DoubleVector p) {
        x = xNext;
        DoubleVector gradient = form.gradient(x);
        final double denominator = (a.multiply(p)).scalar(p);
        // count alpha_k
        final double alpha = -(gradient.scalar(p)) / denominator;
        // update x_k+1
        xNext = x.add(p.multiplyBy(alpha));
        // count beta_k
        gradient = form.gradient(xNext);
        final double beta = (a.multiply(gradient)).scalar(p) / denominator;
        // update p_k+1
        p = (p.multiplyBy(beta)).subtract(gradient);
        return new DoubleVector[]{x, xNext, p};
    } 
    
}
