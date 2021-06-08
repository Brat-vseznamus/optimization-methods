import linear.DoubleVector;
import linear.Matrix;
import org.junit.jupiter.api.Test;
import slau.methods.ConjugateGradientMethod;
import slau.utils.FormulaGenerator;

import java.util.function.Function;
import java.util.stream.IntStream;

public class ConjugateSlauTest {

    private DoubleVector rangeVector(final int n) {
        final DoubleVector x = new DoubleVector(n);
        IntStream.range(0, n).forEach(i -> x.set(i, i + 1d));
        return x;
    }

    private void test(final Function<Integer, Matrix> generator, final int n1, final int n2) {
        final ConjugateGradientMethod conjugate = new ConjugateGradientMethod();
        for (int n = n1; n <= n2; n = (int) (((double) n) * Math.PI)) {
            final Matrix matrix = generator.apply(n);
            final DoubleVector x = rangeVector(n);
            final DoubleVector solve = new DoubleVector(conjugate.solve(matrix, matrix.multiplyBy(x).toArray()));
            final double delta = x.subtract(solve).norm();
            final double minCond = (delta / x.norm()) / (matrix.multiplyBy(x).norm() / x.norm());
            System.out.printf("%d %d %f %f %f%n",
                    n, conjugate.getIterations(), delta, delta / x.norm(), minCond);
        }
    }

    @Test
    public void diagonalDominanceTest() {
        test((n) -> FormulaGenerator.generateDigMatrix(n, 0.5),
                10, 100000);
    }

    @Test
    public void diagonalDominanceNegativeTest() {
        test((n) -> {
                    final Matrix matrix = FormulaGenerator.generateDigMatrix(n, 0.5);
                    IntStream.range(0, n).forEach(row -> {
                        IntStream.range(0, row).forEach(col ->
                                matrix.set(row, col, -matrix.get(row, col)));
                        IntStream.range(row + 1, n).forEach(col ->
                                matrix.set(row, col, -matrix.get(row, col)));
                    });
                    return matrix;
                },
                10, 100000);
    }

    @Test
    public void hilbertTest() {
        test(FormulaGenerator::generateHilbert, 10, 1000);
    }
}
