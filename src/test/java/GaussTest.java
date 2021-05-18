import org.junit.jupiter.api.Test;
import slau.matrix.Matrix;
import slau.matrix.RegularMatrix;
import slau.methods.GaussMethod;

import java.util.Arrays;

import static java.lang.Double.NaN;

public class GaussTest {

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
        TestUtils.assertEqualsDoubleArrays(answer, new GaussMethod().solve(m, numbers));
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
        TestUtils.assertEqualsDoubleArrays(answer, new GaussMethod().solve(m, numbers));
    }

    @Test
    public void gaussD3SwapLinesTest() {
        final Matrix m = new RegularMatrix(
                new double[][]{
                        {1, 2, 3},
                        {1, 2, 4},
                        {0, 5, 6}
                });
        final double[] numbers = new double[]{4, 5, 6};
        final double[] answer = {1d, 0d, 1d};
        TestUtils.assertEqualsDoubleArrays(answer, new GaussMethod().solve(m, numbers));
    }

    @Test
    public void gaussD5DoubleSwapLinesTest() {
        final Matrix m = new RegularMatrix(
                new double[][]{
                        {0, 2, 3, 4, 2},
                        {1, 2, 4, 5, 0},
                        {0, 0, 0, 6, 4},
                        {5, 7, 4, 2, 1},
                        {0, 3, 7, 4, 1}
                });
        final double[] numbers = new double[]{12, 0, 4, 4, 17};
        final double[] answer = {
                -5.97826,
                4.27717,
                0.891304,
                -1.22826,
                2.84239};
        TestUtils.assertEqualsDoubleArrays(answer, new GaussMethod().solve(m, numbers));
    }
}
