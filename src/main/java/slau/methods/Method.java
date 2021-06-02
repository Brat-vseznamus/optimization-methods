package slau.methods;

import matrix.Matrix;

public interface Method {
    double[] solve(Matrix matrix, double[] numbers);
}
