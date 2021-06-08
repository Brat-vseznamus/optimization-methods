import linear.DoubleVector;
import linear.Matrix;
import methods.Pair;
import org.junit.jupiter.api.Test;
import slau.methods.ConjugateGradientMethod;
import slau.methods.Method;
import slau.utils.FormulaGenerator;

import java.util.stream.IntStream;

public class ConjugateSlauTest {

    @Test
    public void hilbertTest() {
        final ConjugateGradientMethod conjugate = new ConjugateGradientMethod();
        for (int n = 10; n <= 1000; n = (int) (((double) n) * Math.E)) {
            final Matrix matrix = FormulaGenerator.generateHilbert(n);
            final DoubleVector x = new DoubleVector(n);
            final DoubleVector solve = new DoubleVector(conjugate.solve(matrix, matrix.multiplyBy(x).toArray()));
            IntStream.range(0, n).forEach(i -> x.set(i, i + 1d));
            final double delta = x.subtract(solve).norm();
            final double minCond = (delta / x.norm()) / (matrix.multiplyBy(x).norm() / x.norm());
            System.out.printf("%d %d %f %f %f%n",
                    n, conjugate.getIterations(), delta, delta / x.norm(), minCond);
        }
    }
}
