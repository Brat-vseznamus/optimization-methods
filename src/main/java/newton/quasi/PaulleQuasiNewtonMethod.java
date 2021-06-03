package newton.quasi;

import expression.Expression;
import linear.DoubleMatrix;
import linear.DoubleVector;
import methods.dimensional.one.GoldenRatioMethod;
import newton.utils.FunctionExpression;

public class PaulleQuasiNewtonMethod extends AbstractQuasiNewtonMethod{
    public PaulleQuasiNewtonMethod(FunctionExpression function) {
        super(function);
    }

    public PaulleQuasiNewtonMethod(Expression function, int n) {
        super(function, n);
    }

    public PaulleQuasiNewtonMethod(FunctionExpression function, double eps) {
        super(function, eps);
    }

    @Override
    protected DoubleVector iteration(DoubleVector x0) {
        DoubleVector dw = function.gradient(x0).multiplyBy(-1).subtract(w);
        w = w.add(dw);
        DoubleVector v = g.multiply(dw);
        DoubleVector dxl = dx.add(v);
        g = (DoubleMatrix) g.subtract(dxl.multiply(dxl).multilpy(1d/dw.scalar(dxl)));
        p = g.multiply(w);
        double alpha = new GoldenRatioMethod(a -> {
            return function.evaluate(x0.add(p.multiplyBy(a)).toArray());
        }).findMin(0, 1);
        DoubleVector x1 = x0.add(p.multiplyBy(alpha));
        dx = x1.subtract(x0);
        return x1;
    }
}
