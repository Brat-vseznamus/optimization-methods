package slau.methods;

import slau.matrix.LU;
import slau.matrix.LUMatrix;
import slau.matrix.Matrix;

public class LUMethod implements Method{
    @Override
    public double[] solve(final Matrix matrix, final double[] numbers) {
        if (!(matrix instanceof LUMatrix)) {
            throw new IllegalArgumentException("Matrix must have LU decomposition.");
        }
        final LU lu = ((LUMatrix) matrix).getLU();
        final Method gauss = new GaussMethod();
        return gauss.solve(lu.getU(),
                gauss.solve(lu.getL(), numbers));
    }
}
