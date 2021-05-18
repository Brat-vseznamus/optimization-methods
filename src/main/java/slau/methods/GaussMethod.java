package slau.methods;

import slau.methods.Method;
import slau.matrix.Matrix;

public class GaussMethod implements Method {

    @Override
    public double[] solve(Matrix matrix, double[] numbers) {
        int len = matrix.getN();
        if (len != numbers.length) {
            throw new IllegalArgumentException("vector of numbers must have same size with matrix");
        }
        double[] vector = new double[len];
        // TODO: handle some special cases
        for (int i = 1; i < len; i++) {
            double aii = matrix.get(i - 1, i - 1);
            for (int ri = i; ri < len; ri++) {
                double aji = matrix.get(ri, i - 1);
                for (int j = i - 1; j < len; j++) {
                    matrix.set(ri, j,
                            matrix.get(ri, j)
                                    - aji / aii * matrix.get(i - 1, j));
                }
                numbers[ri] -= aji / aii * numbers[i - 1];
            }
        }
        for (int i = len - 1; i >= 0; i--) {
            vector[i] = numbers[i];
            for (int j = i + 1; j < len; j++) {
                vector[i] -= vector[j] * matrix.get(i, j);
            }
            vector[i] /= matrix.get(i, i);
        }
        return vector;
    }
    
}
