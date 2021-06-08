package slau.methods;

import linear.DoubleVector;
import linear.Matrices;
import linear.Matrix;

public class ConjugateGradientMethod implements Method {
    public static final double DEFAULT_EPS = 1e-7d;

    private int iterations;

    public int getIterations() {
        return iterations;
    }

    @Override
    public double[] solve(final Matrix matrix, final double[] numbers) {
        Matrices.requireSymmetric(matrix);
//        Matrices.requirePositive(matrix);

        final DoubleVector f = new DoubleVector(numbers);
        DoubleVector x = new DoubleVector(numbers.length);
        DoubleVector r = f.subtract(matrix.multiplyBy(x));
        DoubleVector z = new DoubleVector(r);

        final int maxIterations = numbers.length * 50;
        iterations = 0;
        double rScalarPrev = r.scalar(r);
        double rScalarTemp;
        while (++iterations < maxIterations) {
            final DoubleVector az = matrix.multiplyBy(z);
            final double alpha = rScalarPrev / (az.scalar(z));

            x = x.add(z.multiplyBy(alpha));

            r = r.subtract(az.multiplyBy(alpha));

            if (r.norm() / f.norm() < DEFAULT_EPS) {
                break;
            }

            rScalarTemp = r.scalar(r);

            final double beta = rScalarTemp / rScalarPrev;

            rScalarPrev = rScalarTemp;

            z = r.add(z.multiplyBy(beta));
        }

        return x.toArray();
    }
}
