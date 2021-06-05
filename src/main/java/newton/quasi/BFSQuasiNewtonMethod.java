package newton.quasi;

import expression.Expression;
import linear.DoubleMatrix;
import linear.DoubleVector;
import linear.Matrices;
import methods.dimensional.one.GoldenRatioMethod;
import newton.utils.FunctionExpression;
import newton.utils.Iteration;
import slau.methods.GaussWithMainElementMethod;

public class BFSQuasiNewtonMethod extends AbstractQuasiNewtonMethod {

    public BFSQuasiNewtonMethod(FunctionExpression function) {
        super(function);
    }

    public BFSQuasiNewtonMethod(Expression function, int n) {
        super(function, n);
    }

    public BFSQuasiNewtonMethod(FunctionExpression function, double eps) {
        super(function, eps);
    }

    public BFSQuasiNewtonMethod() { }

    @Override
    protected DoubleVector iteration(DoubleVector x0) {
        DoubleVector wk = function.gradient(x0).multiplyBy(-1);
        DoubleVector dw = wk.subtract(w);
        w = wk;
        updatingG(dw);
        p = g.multiply(w);
        double alpha = new GoldenRatioMethod(a -> {
            return function.evaluate(x0.add(p.multiplyBy(a)).toArray());
        }).findMin(-1, 1);


        DoubleVector x1 = x0.add(p.multiplyBy(alpha));
        dx = x1.subtract(x0);
        return x1;
    }

    protected void updatingG(DoubleVector dw) {
        DoubleVector v = g.multiply(dw);
        double ro = v.scalar(dw);
        DoubleVector r = v.multiplyBy(1/ro).subtract(dx.multiplyBy(1/dx.scalar(dw)));
        g = (DoubleMatrix) g.subtract(dx.multiply(dx).multilpy(1d/dw.scalar(dx)));
        g = (DoubleMatrix) g.subtract(v.multiply(v).multilpy(1/ro)).add(r.multiply(r).multilpy(ro));
    }


}
