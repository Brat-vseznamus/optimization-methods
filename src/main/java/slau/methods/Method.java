package slau.methods;

import linear.Matrix;

public interface Method {
    double[] solve(Matrix matrix, double[] numbers);
}
