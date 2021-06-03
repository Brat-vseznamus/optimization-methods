package newton;

import linear.DoubleVector;

public interface NewtonMethod {
    DoubleVector findMin(DoubleVector x0);
}
