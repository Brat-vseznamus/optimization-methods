import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import slau.matrix.LU;
import slau.matrix.LUMatrix;

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

    private void testTemplate(LUMatrix m) {
        LU lu = m.getLU();
        System.out.println(lu.getL());
        System.out.println(lu.getU());
        Assertions.assertTrue(m.equals(lu.getL().multiply(lu.getU())));
    }

}
