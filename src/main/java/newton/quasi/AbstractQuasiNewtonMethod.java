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
import newton.utils.NewtonMethodWithSavedAlphas;

public abstract class AbstractQuasiNewtonMethod extends AbstractNewtonMethod implements QuasiNewtonMethod, NewtonMethodWithSavedAlphas {
    protected DoubleMatrix g;
    protected DoubleVector w, dx, p;

    public AbstractQuasiNewtonMethod(final FunctionExpression function, final double eps) {
        super(function, eps);
    }

    public AbstractQuasiNewtonMethod(final Expression function, final int n) {
        super(new FunctionExpression(function, n, false));
    }

    public AbstractQuasiNewtonMethod(final FunctionExpression function) {
        super(function, DEFAULT_EPS);
    }

    public AbstractQuasiNewtonMethod() {
        this(Const.ZERO, 1);
    }

    @Override
    public DoubleVector findMin(final DoubleVector x0) {
        table.clear();
        g = Matrices.E(n);
        w = function.gradient(x0).multiplyBy(-1);
        p = w;
        final DoubleVector finalX = x0;
        final double alpha = new GoldenRatioMethod(a -> {
            return function.evaluate(finalX.add(p.multiplyBy(a)).toArray());
        }).findMin(0, 1);
        addAlpha(alpha);
        final DoubleVector x1 = x0.add(p.multiplyBy(alpha));
        dx = x1.subtract(x0);
        table.add(new Iteration(
                x0,
                function.evaluate(x0.toArray()),
                x1,
                function.evaluate(x1.toArray()),
                function.gradient(x0)));
        return super.iterate(x1);
    }
}
