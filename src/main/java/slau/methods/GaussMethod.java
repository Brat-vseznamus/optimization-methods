package slau.methods;

import slau.methods.Method;
import slau.matrix.Matrix;

import java.util.Arrays;

public class GaussMethod implements Method {

    @Override
    public double[] solve(final Matrix matrix, final double[] numbers) {
        final int len = matrix.getN();
        if (len != numbers.length) {
            throw new IllegalArgumentException("vector of numbers must have same size with matrix");
        }
        final double[] vector = new double[len];
        // TODO: handle some special cases
        for (int i = 1; i < len; i++) {
            double aii = matrix.get(i - 1, i - 1);
            if (aii == 0) {
                final int curLine = i - 1;
                for (int j = curLine + 1; j < len; j++) {
                    if (matrix.get(j, i - 1) != 0) {
                        matrix.swapRows(j, curLine);
                        final double old = numbers[curLine];
                        numbers[curLine] = numbers[j];
                        numbers[j] = old;
                        aii = matrix.get(i - 1, i - 1);
                        break;
                    }
                }
            }

            for (int ri = i; ri < len; ri++) {
                final double aji = matrix.get(ri, i - 1);
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
