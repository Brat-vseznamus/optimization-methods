package newton.quasi;

import expression.Expression;
import linear.DoubleMatrix;
import linear.DoubleVector;
import newton.utils.FunctionExpression;

public class PaulleQuasiNewtonMethod extends BFSQuasiNewtonMethod{
    public PaulleQuasiNewtonMethod(FunctionExpression function) {
        super(function);
    }

    public PaulleQuasiNewtonMethod(Expression function, int n) {
        super(function, n);
    }

    public PaulleQuasiNewtonMethod(FunctionExpression function, double eps) {
        super(function, eps);
    }

    public PaulleQuasiNewtonMethod() { }

    @Override
    protected void updatingG(DoubleVector dw) {
        DoubleVector dxl = dx.add(g.multiply(dw));
        g = (DoubleMatrix) g.subtract(dxl.multiply(dxl).multilpy(1d/dw.scalar(dxl)));
    }
}
