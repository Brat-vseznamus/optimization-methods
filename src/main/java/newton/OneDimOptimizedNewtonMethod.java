package newton;

import linear.DoubleVector;
import methods.dimensional.one.GoldenRatioMethod;
import newton.utils.FunctionExpression;
import slau.methods.GaussMethod;

import java.util.function.UnaryOperator;

public class OneDimOptimizedNewtonMethod extends AbstractNewtonMethod {

    public OneDimOptimizedNewtonMethod(FunctionExpression function, double eps) {
        super(function, eps);
    }

    public OneDimOptimizedNewtonMethod(FunctionExpression function) {
        super(function);
    }

    public OneDimOptimizedNewtonMethod() {
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
        UnaryOperator<Double> oneDimOpt = aplha -> {
            return function.evaluate(x0.add(pk.multiplyBy(aplha)).toArray());
        };
        double alphak = new GoldenRatioMethod(oneDimOpt).findMin(0, 1);
        return x0.add(pk.multiplyBy(alphak));
    }
}
