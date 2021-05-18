import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import slau.matrix.Matrix;
import slau.matrix.RegularMatrix;
import slau.methods.GaussMethod;

import java.util.stream.IntStream;

import static java.lang.Double.NaN;

public class GaussTest {

    final static double DOUBLE_EQUALS_EPS = 0.0001;

    @Test
    public void gaussD3Test() {
        final Matrix m = new RegularMatrix(
                new double[][]{
                        {1, 2, 3},
                        {2, 3, 4},
                        {3, 5, 6}
                });
        final double[] numbers = new double[]{4, 5, 6};
        final double[] answer = {1d, -3d, 3d};
        assertEqualsDoubleArrays(answer, new GaussMethod().solve(m, numbers));
    }

    @Test
    public void gaussD4_many_solutions() {
        final Matrix m = new RegularMatrix(
                new double[][]{
                        {2, -1, 3, -5},
                        {7, -5, -9, -10},
                        {1, -1, -5, 0},
                        {3, -2, -2, -5}
                });
        final double[] numbers = new double[]{1, 8, 2, 3};
        final double[] answer = {NaN, NaN, NaN, NaN};
        assertEqualsDoubleArrays(answer, new GaussMethod().solve(m, numbers));
    }

    private void assertEqualsDoubleArrays(final double[] first,
                                          final double[] second) {
        Assertions.assertEquals(first.length, second.length);
        IntStream.range(0, first.length).forEach(index -> Assertions.assertEquals(first[index], second[index], DOUBLE_EQUALS_EPS));
    }
}
