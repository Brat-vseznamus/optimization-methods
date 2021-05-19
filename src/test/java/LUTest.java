import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import slau.matrix.*;

import java.util.stream.IntStream;

public class LUTest {
    @Test
    public void LU_D4_test() {
        testTemplate(
                new LUMatrix(
                        new double[][]{
                                {3, 4, -9, 5},
                                {-15, -12, 50, -16},
                                {-27, -36, 73, 8},
                                {9, 12, -10, -16}
                        }
                ));
    }

    @Test
    public void LU_D3_test() {
        testTemplate(
                new LUMatrix(
                        new double[][]{
                                {1, 2, 1},
                                {2, 1, 1},
                                {1, -1, 2},
                        }
                ));
    }

    private void testTemplate(final LUMatrix m) {
        final LU lu = m.getLU();
        System.out.println(lu.getL());
        System.out.println(lu.getU());
        Assertions.assertTrue(m.equals(lu.getL().multiply(lu.getU())));
    }

    @Test
    public void ProfileMatrixLU_test() {
        final double[][] data = {
                {1, 5, 0, 0, 7},
                {0, 3, 2, 0, 0},
                {1, 0, 2, 0, 0},
                {0, 4, 6, 1, 0},
                {0, 2, 5, 0, 5}};
        final LUDecomposible m = new ProfileMatrix(
                data
        );
        final Matrix mCopy = new RegularMatrix(data);

        final LU lu = m.getLU();
        final Matrix l = lu.getL();
        final Matrix u = lu.getU();

        Assertions.assertTrue(l.multiply(u).equals(mCopy), "L * U must equal M");

        final boolean lZeros = IntStream.range(0, data.length)
                .allMatch(i -> IntStream.range(i + 1, data.length)
                        .allMatch(j -> l.get(i, j) == 0d));
        Assertions.assertTrue(lZeros, "L must have zeros in right upper triangle");

        final boolean uZeros = IntStream.range(0, data.length)
                .allMatch(i -> IntStream.range(0, i)
                        .allMatch(j -> u.get(i, j) == 0d));
        Assertions.assertTrue(uZeros, "U must have zeros in left down triangle");

        final boolean uOnes = IntStream.range(0, data.length).allMatch(i -> u.get(i, i) == 1d);
        Assertions.assertTrue(uOnes, "U must have ones in diagonal");
    }
}
