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
        for (int i = 1; i < len; i++) {
            double aii = matrix.get(i - 1, i - 1);
            double ajj = matrix.get(i, i);
            for (int j = i - 1; j < len; j++) {
                matrix.set(i, j,
                        matrix.get(i, j)
                                - ajj / aii * matrix.get(i - 1, j));
            }
            numbers[i] -= ajj / aii * numbers[i - 1];
        }
        return null;
    }
    
}
