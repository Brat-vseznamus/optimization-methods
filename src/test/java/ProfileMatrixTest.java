import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import matrix.Matrix;
import matrix.ProfileMatrix;
import matrix.RegularMatrix;
import slau.utils.FormulaGenerator;

import java.nio.file.Path;

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

    @Test
    public void profileMatrixToAndFromFile() {
        final int n = 100;
        final int k = 4;
        final RegularMatrix regularMatrix = (RegularMatrix) FormulaGenerator.generateMatrix(n, k);
        final Path fileToSave = Path.of("./matrices/profile/matrix.txt");
        final ProfileMatrix matrix = new ProfileMatrix(regularMatrix.getData());
        matrix.saveToFile(fileToSave);
        final ProfileMatrix matrixFromFile = new ProfileMatrix(fileToSave);
        Assertions.assertTrue(matrix.equals(matrixFromFile));
    }
}
