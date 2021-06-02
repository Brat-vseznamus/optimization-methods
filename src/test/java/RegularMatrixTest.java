import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import matrix.RegularMatrix;
import slau.utils.FormulaGenerator;

import java.nio.file.Path;

public class RegularMatrixTest {

    @Test
    public void regularMatrixToAndFromFile() {
        final int n = 100;
        final int k = 4;
        final RegularMatrix matrix = (RegularMatrix) FormulaGenerator.generateMatrix(n, k);
        final Path fileToSave = Path.of("./matrices/regular/matrix.txt");
        matrix.saveToFile(fileToSave);
        final RegularMatrix matrixFromFile = new RegularMatrix(fileToSave);
        Assertions.assertTrue(matrix.equals(matrixFromFile));
    }
}
