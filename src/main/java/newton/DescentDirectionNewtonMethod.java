package newton;

import linear.DoubleVector;
import methods.dimensional.one.GoldenRatioMethod;
import newton.utils.FunctionExpression;
import slau.methods.GaussMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

public class DescentDirectionNewtonMethod extends AbstractNewtonMethod {
    private final List<Double> alphas = new ArrayList<>();

    public DescentDirectionNewtonMethod(final FunctionExpression function, final double eps) {
        super(function, eps);
    }

    public DescentDirectionNewtonMethod(final FunctionExpression function) {
        super(function);
    }

    public DescentDirectionNewtonMethod() {
        super();
    }

    @Override
    protected DoubleVector iteration(final DoubleVector x0) {
        DoubleVector pk = new DoubleVector(
                new GaussMethod()
                        .solve(
                                function.hessian(x0),
                                function.gradient(x0).multiplyBy(-1).toArray()
                        ));
        if (pk.scalar(function.gradient(x0)) > 0) {
            pk = function.gradient(x0).multiplyBy(-1);
        }
        final DoubleVector finalPk = pk;
        final UnaryOperator<Double> oneDimOpt = aplha -> {
            return function.evaluate(x0.add(finalPk.multiplyBy(aplha)).toArray());
        };
        final double alphak = new GoldenRatioMethod(oneDimOpt).findMin(0, 1);
        alphas.add(alphak);
        return x0.add(pk.multiplyBy(alphak));
    }

    @Override
    public DoubleVector findMin(final DoubleVector x0) {
        alphas.clear();
        return super.findMin(x0);
    }

    public List<Double> getAlphas() {
        return alphas;
    }
}
