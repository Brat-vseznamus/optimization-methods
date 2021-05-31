import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import slau.matrix.Matrix;
import slau.matrix.MatrixWithE;
import slau.matrix.RegularMatrix;
import slau.methods.GaussMethod;
import slau.methods.GaussWithMainElementMethod;

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
    public void gaussReversedTest() {
        final Matrix m = new RegularMatrix(
                new double[][]{
                        {1, 2, 3},
                        {2, 3, 4},
                        {3, 5, 6}
                });
        final Matrix answer = new RegularMatrix(
                new double[][]{
                        {-2, 3, -1},
                        {0, -3, 2},
                        {1, 1, -1}
                });
        final MatrixWithE preReversed = new MatrixWithE(m);
        new GaussMethod().solve(preReversed, new double[m.getN()]);
        Assertions.assertTrue(preReversed.getReversed().equals(answer));
    }


    @Test
    public void gaussReversedWithSwapTest() {
        final Matrix m = new RegularMatrix(
                new double[][]{
                        {3, 2, 2},
                        {0, 2, 4},
                        {1, 2, 2}
                });
        final Matrix answer = new RegularMatrix(
                new double[][]{
                        {0.5, 0, -0.5},
                        {-0.5, -0.5, 1.5},
                        {0.25, 0.5, -0.75}
                });
        final MatrixWithE preReversed = new MatrixWithE(m);
        new GaussMethod().solve(preReversed, new double[m.getN()]);
        Assertions.assertTrue(preReversed.getReversed().equals(answer));
    }

    @Test
    public void mainGaussD3Test() {
        final Matrix m = new RegularMatrix(
                new double[][]{
                        {1, 2, 3},
                        {2, 3, 4},
                        {3, 5, 6}
                });
        final double[] numbers = new double[]{4, 5, 6};
        final double[] answer = {1d, -3d, 3d};
        TestUtils.assertEqualsDoubleArrays(answer, new GaussWithMainElementMethod().solve(m, numbers));
    }

    @Test
    public void mainGaussD3SwapLinesTest() {
        final Matrix m = new RegularMatrix(
                new double[][]{
                        {1, 2, 3},
                        {1, 2, 4},
                        {0, 5, 6}
                });
        final double[] numbers = new double[]{4, 5, 6};
        final double[] answer = {1d, 0d, 1d};
        TestUtils.assertEqualsDoubleArrays(answer, new GaussWithMainElementMethod().solve(m, numbers));
    }

    @Test
    public void MainGaussD5DoubleSwapLinesTest() {
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
        TestUtils.assertEqualsDoubleArrays(answer, new GaussWithMainElementMethod().solve(m, numbers));
    }
}
