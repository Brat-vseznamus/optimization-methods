import org.junit.jupiter.api.Assertions;
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
        Assertions.assertTrue(Arrays.equals(answer,
                new GaussMethod().solve(m, numbers)));
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
        Assertions.assertTrue(Arrays.equals(answer,
                new GaussMethod().solve(m, numbers)));
    }
}
