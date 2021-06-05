package newton;

import linear.DoubleVector;
import newton.utils.FunctionExpression;
import slau.methods.GaussMethod;
import slau.methods.GaussWithMainElementMethod;

public class ClassicNewtonMethod extends AbstractNewtonMethod {

    public ClassicNewtonMethod(final FunctionExpression function, final double eps) {
        super(function, eps);
    }

    public ClassicNewtonMethod(final FunctionExpression function) {
        super(function);
    }

    public ClassicNewtonMethod() {
        super();
    }

    @Override
    protected DoubleVector iteration(final DoubleVector x0) {
        final DoubleVector pk = new DoubleVector(
                new GaussWithMainElementMethod()
                        .solve(
                                function.hessian(x0),
                                function.gradient(x0).multiplyBy(-1).toArray()
                        ));
        return x0.add(pk);
    }
}
