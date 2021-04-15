package functions;

public abstract class AbstractGradientMethod implements OptimizationMethod {
    protected QuadraticForm form;
    protected double eps;

    protected AbstractGradientMethod(QuadraticForm form, double eps) {
        this.form = form;
        this.eps = eps;
    }

    protected AbstractGradientMethod(QuadraticForm form) {
        this(form, 1e-5d);
    }
}
