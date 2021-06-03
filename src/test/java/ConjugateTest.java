import org.junit.jupiter.api.Test;
import linear.Matrix;
import linear.RegularMatrix;
import slau.methods.ConjugateGradientMethod;

import java.util.Arrays;

public class ConjugateTest {

    @Test
    public void conjugateEasyTest() {
        final Matrix m = new RegularMatrix(
                new double[][]{
                        {1, 0, 0, 0, 0, 0},
                        {0, 1, 0, 0, 0, 0},
                        {0, 0, 1, 0, 0, 0},
                        {0, 0, 0, 1, 0, 0},
                        {0, 0, 0, 0, 1, 0},
                        {0, 0, 0, 0, 0, 1}
                });
        final double[] numbers = {1, 1, 1, 1, 1, 1};
        final double[] answer = {1, 1, 1, 1, 1, 1};
        final double[] solve = new ConjugateGradientMethod().solve(m, numbers);
        TestUtils.assertEqualsDoubleVectorNorms(answer, solve);
    }

    @Test
    public void conjugateEasy2Test() {
        final Matrix m = new RegularMatrix(
                new double[][]{
                        {1, 0, 0, 0, 0, 0},
                        {0, 5, 0, 0, 0, 0},
                        {0, 0, -10, 0, 0, 0},
                        {0, 0, 0, 8, 0, 0},
                        {0, 0, 0, 0, 2, 0},
                        {0, 0, 0, 0, 0, 9}
                });
        final double[] numbers = {1, 5, -10, 8, 2, 9};
        final double[] answer = {1, 1, 1, 1, 1, 1};
        final double[] solve = new ConjugateGradientMethod().solve(m, numbers);
        System.out.println(Arrays.toString(solve));
        TestUtils.assertEqualsDoubleVectorNorms(answer, solve);
    }

    @Test
    public void conjugateEasy3Test() {
        final Matrix m = new RegularMatrix(
                new double[][]{
                        {1, 0, 0, 0, 0, 0},
                        {0, 5, 0, 0, 0, 0},
                        {0, 0, -10, 0, 0, 0},
                        {0, 0, 0, 8, 0, 0},
                        {0, 0, 0, 0, 2, 0},
                        {0, 0, 0, 0, 0, 9}
                });
        final double[] answer = {3, -1, -10, 17, 18, -20.5};
        final double[] numbers = {3, -5, 100, 136, 36, -184};
        final double[] solve = new ConjugateGradientMethod().solve(m, numbers);
        System.out.println(Arrays.toString(solve));
        TestUtils.assertEqualsDoubleVectorNorms(answer, solve);
    }

    @Test
    public void conjugateD3Test() {
        final Matrix m = new RegularMatrix(
                new double[][]{
                        {1, 2, -30},
                        {2, 5, 4},
                        {3, 5, 6}
                });
        final double[] answer = {1d, -3d, 3d};
        final double[] numbers = new double[]{-95d, -1d, 6d};
        final double[] solve = new ConjugateGradientMethod().solve(m, numbers);
        TestUtils.assertEqualsDoubleArrays(answer, solve);
    }

}
