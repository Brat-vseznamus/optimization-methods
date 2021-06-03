package newton;

import linear.DoubleVector;
import newton.utils.FunctionExpression;
import newton.utils.Iteration;

import java.util.List;

public interface NewtonMethod {
    double DEFAULT_EPS = 1e-5d;

    void setFunction(FunctionExpression function);

    DoubleVector findMin(DoubleVector x0);

    List<Iteration> getTable();
}
