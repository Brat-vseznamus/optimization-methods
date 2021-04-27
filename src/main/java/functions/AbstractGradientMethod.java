package functions;

public abstract class AbstractGradientMethod implements OptimisationGradientMethod {
    protected QuadraticForm form;
    protected double eps;

    protected AbstractGradientMethod(final QuadraticForm form, final double eps) {
        this.form = form;
        this.eps = eps;
    }

    protected AbstractGradientMethod(final QuadraticForm form) {
        this(form, 1e-5d);
    }
}
