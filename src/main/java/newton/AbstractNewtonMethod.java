package newton;

import linear.DoubleVector;
import newton.utils.FunctionExpression;

public abstract class AbstractNewtonMethod implements NewtonMethod {
    protected FunctionExpression function;
    protected int n;
    protected double eps;

    public AbstractNewtonMethod(FunctionExpression function) {
        this.function = function;
        n = function.getN();
    }

    public AbstractNewtonMethod(FunctionExpression function, double eps) {
        this(function);
        this.eps = eps;
    }

    protected abstract DoubleVector iteration(DoubleVector x0);

    @Override
    public DoubleVector findMin(DoubleVector x0) {
        DoubleVector x1 = x0;
        int cnt = 100*n;
        do {
            x0 = x1;
            x1 = iteration(x0);
        } while (x1.subtract(x0).norm() > eps && cnt-- > 0);
        if (cnt == 0) {
            return null;
        }
        return x1;
    }
}