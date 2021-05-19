package matrixGeneration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import slau.matrix.Matrix;
import slau.matrix.RegularMatrix;
import slau.utils.FormulaGenerator;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.IntStream;

public class DifferentVariantsOfMatrix {

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
