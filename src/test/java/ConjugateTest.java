import linear.Matrix;
import linear.RegularMatrix;
import org.junit.jupiter.api.Test;
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
                        {0, 0, 10, 0, 0, 0},
                        {0, 0, 0, 8, 0, 0},
                        {0, 0, 0, 0, 2, 0},
                        {0, 0, 0, 0, 0, 9}
                });
        final double[] numbers = {1, 5, 10, 8, 2, 9};
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
                        {0, 0, 10, 0, 0, 0},
                        {0, 0, 0, 8, 0, 0},
                        {0, 0, 0, 0, 2, 0},
                        {0, 0, 0, 0, 0, 9}
                });
        final double[] answer = {3, -1, -10, 17, 18, -20.5};
        final double[] numbers = {3, -5, -100, 136, 36, -184.5};
        final double[] solve = new ConjugateGradientMethod().solve(m, numbers);
        System.out.println(Arrays.toString(solve));
        TestUtils.assertEqualsDoubleVectorNorms(answer, solve);
    }

    @Test
    public void conjugateD3Test() {
        final Matrix m = new RegularMatrix(
                new double[][]{
                        {1, 2, 3},
                        {2, 5, 4},
                        {3, 4, 60}
                });
        final double[] answer = {1, -3, 3};
        final double[] numbers = new double[]{4, -1, 171};
        final double[] solve = new ConjugateGradientMethod().solve(m, numbers);

        TestUtils.assertEqualsDoubleArrays(answer, solve);
    }

    @Test
    public void conjugateD4Test() {
        final Matrix m = new RegularMatrix(
                new double[][]{
                        {341, -20, -12, 0},
                        {-20,  34,   0, 0},
                        {-12,   0,  90, 0},
                        {  0,   0,   0, 3}
                });
        final double[] answer = {3, 0, 234, 9};
        final double[] numbers = new double[]{-1785, -60, 21024, 27};
        final double[] solve = new ConjugateGradientMethod().solve(m, numbers);
        System.out.println(Arrays.toString(solve));
        TestUtils.assertEqualsDoubleArrays(answer, solve);
    }

    @Test
    public void conjugateDiagPreobD3Test() {
        final Matrix m = new RegularMatrix(
                new double[][]{
                        { 2,  0, -3},
                        { 0,  4, -4},
                        {-3, -4,  7}
                });
        final double[] answer = {1d, -3d, 3d};
        final double[] numbers = m.multiplyBy(answer);
        final double[] solve = new ConjugateGradientMethod().solve(m, numbers);
        System.out.println(Arrays.toString(solve));
        TestUtils.assertEqualsDoubleArrays(answer, solve);
    }
}
