package slau.methods;

import linear.LU;
import linear.LUDecomposible;
import linear.Matrix;

public class LUMethod implements Method{
    @Override
    public double[] solve(final Matrix matrix, final double[] numbers) {
        if (!(matrix instanceof LUDecomposible)) {
            throw new IllegalArgumentException("Matrix must have LU decomposition.");
        }
        final LU lu = ((LUDecomposible) matrix).getLU();
        final Method gauss = new GaussMethod();
        return gauss.solve(lu.getU(),
                gauss.solve(lu.getL(), numbers));
    }
}
