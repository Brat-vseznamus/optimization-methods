package newton;

import linear.DoubleVector;
import newton.utils.FunctionExpression;
import slau.methods.GaussMethod;

public class ClassicNewtonMethod extends AbstractNewtonMethod {

    public ClassicNewtonMethod(FunctionExpression function, double eps) {
        super(function, eps);
    }

    public ClassicNewtonMethod(FunctionExpression function) {
        super(function);
    }

    public ClassicNewtonMethod() {
        super();
    }

    @Override
    protected DoubleVector iteration(DoubleVector x0) {
        DoubleVector pk = new DoubleVector(
                new GaussMethod()
                        .solve(
                                function.hessian(x0),
                                function.gradient(x0).multiplyBy(-1).toArray()
                        ));
        return x0.add(pk);
    }
}
