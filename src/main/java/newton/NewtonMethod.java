package newton;

import linear.DoubleVector;
import newton.utils.Iteration;

import java.util.List;

public interface NewtonMethod {
    DoubleVector findMin(DoubleVector x0);
    List<Iteration> getTable();
}
