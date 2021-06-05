package newton;

import expression.Const;
import linear.DoubleVector;
import newton.utils.FunctionExpression;
import newton.utils.Iteration;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractNewtonMethod implements NewtonMethod {
    protected FunctionExpression function;
    protected int n;
    protected double eps;
    protected List<Iteration> table = new ArrayList<>();

    public AbstractNewtonMethod(final FunctionExpression function, final double eps) {
        this.function = function;
        n = function.getN();
        this.eps = eps;
    }

    public AbstractNewtonMethod(final FunctionExpression function) {
        this(function, DEFAULT_EPS);
    }

    public AbstractNewtonMethod() {
        this(new FunctionExpression(new Const(0), 2, true));
    }

    @Override
    public void setFunction(final FunctionExpression function) {
        this.function = function;
        n = function.getN();
    }

    protected abstract DoubleVector iteration(DoubleVector x0);

    @Override
    public DoubleVector findMin(final DoubleVector x0) {
        table.clear();
        return iterate(x0);
    }

    protected DoubleVector iterate(DoubleVector x0) {
        DoubleVector x1 = x0;
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


    @Override
    public List<Iteration> getTable() {
        return table;
    }

}