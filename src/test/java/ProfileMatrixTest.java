import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import slau.matrix.Matrix;
import slau.matrix.ProfileMatrix;
import slau.matrix.RegularMatrix;

public class ProfileMatrixTest {

    @Test
    public void profileMatrixConstructorTest() {
        final Matrix profileMatrix = new ProfileMatrix(
                new double[][]{
                        {1, 2, 3},
                        {2, 3, 4},
                        {3, 5, 6}}
        );
        final Matrix matrix1 = new RegularMatrix(
                new double[][]{
                        {1, 2, 3},
                        {2, 3, 4},
                        {3, 5, 6}}
        );
        Assertions.assertTrue(profileMatrix.equals(matrix1));
    }

    @Test
    public void profileMatrixWithNonTrivialProfileConstructorTest() {
        final Matrix profileMatrix = new ProfileMatrix(
                new double[][]{
                        {1, 2, 3, 0, 1},
                        {0, 3, 4, 0, 0},
                        {3, 5, 6, 0, 0},
                        {0, 1, 0, 4, 0},
                        {0, 1, 2, 4, 2}}
        );
        final Matrix matrix1 = new RegularMatrix(
                new double[][]{
                        {1, 2, 3, 0, 1},
                        {0, 3, 4, 0, 0},
                        {3, 5, 6, 0, 0},
                        {0, 1, 0, 4, 0},
                        {0, 1, 2, 4, 2}}
        );
        System.out.println(profileMatrix.get(0, 3));
        Assertions.assertTrue(profileMatrix.equals(matrix1));
    }
}
