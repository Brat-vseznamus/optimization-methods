package slau.methods;

import methods.dimensional.poly.DoubleVector;
import slau.matrix.Matrix;

public class ConjugateGradientMethod implements Method {
    public static final double DEFAULT_EPS = 1e-7d;

    @Override
    public double[] solve(final Matrix matrix, final double[] numbers) {
        final DoubleVector b = new DoubleVector(numbers);

        DoubleVector x = new DoubleVector(numbers.length);
        DoubleVector r = b.subtract(new DoubleVector(matrix.multiplyBy(x.toArray())));
        DoubleVector z = r;

        final int maxIterations = numbers.length * 50;
        int iteration = 0;
        double scalarProdPrev = r.scalar(r);
        double scalarProdTemp;
        while (++iteration < maxIterations) {
            final double alpha = scalarProdPrev
                    / (new DoubleVector(matrix.multiplyBy(z.toArray())).scalar(z));

            x = x.add(z.multiplyBy(alpha));

            r = r.subtract(new DoubleVector(matrix.multiplyBy(z.toArray())).multiplyBy(alpha));

            if (r.norm() / b.norm() < DEFAULT_EPS) {
                break;
            }

            scalarProdTemp = r.scalar(r);

            final double beta = scalarProdTemp / scalarProdPrev;

            scalarProdPrev = scalarProdTemp;

            z = r.add(z.multiplyBy(beta));
        }

        return x.toArray();
    }
}
