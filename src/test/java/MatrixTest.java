import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import linear.Matrix;
import linear.RegularMatrix;

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

    @Test
    public void testDeterminant1() {
        final double[][] data = new double[][]{{1}};
        final Matrix matrix = new RegularMatrix(data);

        Assertions.assertTrue(Math.abs(1 - matrix.minor(0, 0, 1)) < TestUtils.DOUBLE_EQUALS_EPS);
    }

    @Test
    public void testDeterminant2() {
        double[][] data = new double[][]{
                {1, 2},
                {4, -100}
        };
        Matrix matrix = new RegularMatrix(data);

        double det = matrix.minor(0, 0, 2);
        System.out.println(det);
        Assertions.assertTrue(Math.abs(-108 - det) < TestUtils.DOUBLE_EQUALS_EPS);

        data = new double[][]{
                {5, 6},
                {8, 10}
        };
        matrix = new RegularMatrix(data);

        det = matrix.minor(0, 0, 2);
        System.out.println(det);
        Assertions.assertTrue(Math.abs(2 - det) < TestUtils.DOUBLE_EQUALS_EPS);

        data = new double[][]{
                {2, 3},
                {8, 10}
        };
        matrix = new RegularMatrix(data);

        det = matrix.minor(0, 0, 2);
        System.out.println(det);
        Assertions.assertTrue(Math.abs(-4 - det) < TestUtils.DOUBLE_EQUALS_EPS);

        data = new double[][]{
                {2, 3},
                {5, 6}
        };
        matrix = new RegularMatrix(data);

        det = matrix.minor(0, 0, 2);
        System.out.println(det);
        Assertions.assertTrue(Math.abs(-3 - det) < TestUtils.DOUBLE_EQUALS_EPS);
    }

    @Test
    public void testDeterminant3() {
        final double[][] data = new double[][]{
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 10}
        };
        final Matrix matrix = new RegularMatrix(data);

        final double det = matrix.determinant();
        System.out.println(det);
        Assertions.assertTrue(Math.abs(-3 - det) < TestUtils.DOUBLE_EQUALS_EPS);
    }

    @Test
    public void testDeterminant4() {
        final double[][] data = new double[][]{
                {9, 13, 5, 2},
                {1, 11, 7, 6},
                {3, 7, 4, 1},
                {6, 0, 7, 10}
        };
        final Matrix matrix = new RegularMatrix(data);

        final double det = matrix.determinant();
        System.out.println(det);
        Assertions.assertTrue(Math.abs(1686D - det) < TestUtils.DOUBLE_EQUALS_EPS);
    }
}
