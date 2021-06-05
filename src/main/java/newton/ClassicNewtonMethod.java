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
        DoubleVector pk = new DoubleVector(
                new GaussWithMainElementMethod()
                        .solve(
                                function.hessian(x0),
                                function.gradient(x0).multiplyBy(-1).toArray()
                        ));
        if (pk.stream().anyMatch(Double::isNaN)) {
            pk = function.gradient(x0).multiplyBy(-1);
        }
        return x0.add(pk);
    }
}
