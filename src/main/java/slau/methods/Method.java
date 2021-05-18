package slau.methods;

import slau.matrix.Matrix;

public interface Method {
    double[] solve(Matrix matrix, double[] numbers);
}
