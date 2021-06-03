package newton.quasi;

import expression.Const;
import expression.Expression;
import linear.DoubleMatrix;
import linear.DoubleVector;
import linear.Matrices;
import methods.dimensional.one.GoldenRatioMethod;
import newton.AbstractNewtonMethod;
import newton.utils.FunctionExpression;
import newton.utils.Iteration;

public abstract class AbstractQuasiNewtonMethod extends AbstractNewtonMethod implements QuasiNewtonMethod {
    protected DoubleMatrix g;
    protected DoubleVector w, dx, p;

    public AbstractQuasiNewtonMethod(FunctionExpression function, double eps) {
        super(function, eps);
    }

    public AbstractQuasiNewtonMethod(Expression function, int n) {
        super(new FunctionExpression(function, n, false));
    }

    public AbstractQuasiNewtonMethod(FunctionExpression function) {
        super(function, DEFAULT_EPS);
    }

    public AbstractQuasiNewtonMethod() {
        this(Const.ZERO, 1);
    }
/*
    @Override
    public void setFunction(final FunctionExpression function) {
        this.function = function;
        g = Matrices.E(function.getN());
    }*/

    @Override
    public DoubleVector findMin(DoubleVector x0) {
        table.clear();
        g = Matrices.E(n);
        w = function.gradient(x0).multiplyBy(-1);
        p = w;
        DoubleVector finalX = x0;
        double alpha = new GoldenRatioMethod(a -> {
            return function.evaluate(finalX.add(p.multiplyBy(a)).toArray());
        }).findMin(0, 1);
        DoubleVector x1 = x0.add(p.multiplyBy(alpha));
        dx = x1.subtract(x0);
        int cnt = 100*n;
        do {
            x0 = x1;
            x1 = iteration(x0);
            table.add(new Iteration(
                    x0,
                    function.evaluate(x0.toArray()),
                    x1,
                    function.evaluate(x1.toArray()),
                    function.gradient(x0)));
        } while (x1.subtract(x0).norm() > eps && cnt-- > 0);
        if (cnt == 0) {
            return null;
        }
        return x1;
    }
}
