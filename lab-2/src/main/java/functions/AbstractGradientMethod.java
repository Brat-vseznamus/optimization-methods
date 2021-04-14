package functions;

public abstract class AbstractGradientMethod implements OptimisationMethod {
    protected QuadraticForm form;
    protected double eps;

    protected AbstractGradientMethod(QuadraticForm form, double eps) {
        this.form = form;
        this.eps = eps;
    }

    protected AbstractGradientMethod(QuadraticForm form) {
        this(form, 1e-6d);
    }
}
