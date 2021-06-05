package newton.quasi;

import expression.Expression;
import linear.DoubleMatrix;
import linear.DoubleVector;
import newton.utils.FunctionExpression;

public class PaulleQuasiNewtonMethod extends BFSQuasiNewtonMethod{
    public PaulleQuasiNewtonMethod(final FunctionExpression function) {
        super(function);
    }

    public PaulleQuasiNewtonMethod(final Expression function, final int n) {
        super(function, n);
    }

    public PaulleQuasiNewtonMethod(final FunctionExpression function, final double eps) {
        super(function, eps);
    }

    public PaulleQuasiNewtonMethod() { }

    @Override
    protected void updatingG(final DoubleVector dw) {
        final DoubleVector dxl = dx.add(g.multiply(dw));
        g = (DoubleMatrix) g.subtract(dxl.multiply(dxl).multilpy(1d/dw.scalar(dxl)));
    }
}
