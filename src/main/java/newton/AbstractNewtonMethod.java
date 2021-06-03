package newton;

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
        table.clear();
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