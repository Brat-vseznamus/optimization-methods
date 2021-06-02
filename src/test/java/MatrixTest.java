import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import matrix.Matrix;
import matrix.RegularMatrix;

public class MatrixTest {

    @Test
    public void equalsTest() {
        final Matrix matrix1 = new RegularMatrix(
                new double[][]{
                        {1, 2, 3},
                        {2, 3, 4},
                        {3, 5, 6}});
        final Matrix matrix2 = new RegularMatrix(
                new double[][]{
                        {1, 2, 3},
                        {2, 3, 4},
                        {3, 5, 6}});
        Assertions.assertTrue(matrix1.equals(matrix2));
    }

    @Test
    public void swapLinesTest() {
        final Matrix matrix = new RegularMatrix(
                new double[][]{
                        {1, 2, 3},
                        {2, 3, 4},
                        {3, 5, 6}});
        final Matrix answer = new RegularMatrix(
                new double[][]{
                        {2, 3, 4},
                        {1, 2, 3},
                        {3, 5, 6}});
        matrix.swapRows(0, 1);
        Assertions.assertTrue(matrix.equals(answer));
    }
}
