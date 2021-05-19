package matrixGeneration;

import org.junit.jupiter.api.Test;
import slau.matrix.Matrix;
import slau.utils.FormulaGenerator;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.IntStream;

public class DifferentVariantsOfMatrix {

    @Test
    public void regularMatrixToFile() {
        final int n = 100;
        final int k = 4;
        final Matrix matrix = FormulaGenerator.generateMatrix(n, k);
        final Path fileToSave = Path.of("./matrices/regular/matrix.txt");
        createDir(fileToSave);
        try (final BufferedWriter writer = Files.newBufferedWriter(fileToSave)) {
            IntStream.range(0, n).forEach(row -> {
                IntStream.range(0, n).forEach(col -> {
                    try {
                        writer.write(getFormattedDouble(matrix.get(row, col)));
                        writer.write(" ");
                    } catch (final IOException e) {
                        System.err.println("Exception while writing: " + e.getMessage());
                    }
                });
                try {
                    writer.write(System.lineSeparator());
                } catch (final IOException e) {
                    System.err.println("Exception while writing: " + e.getMessage());
                }
            });
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    private String getFormattedDouble(final double x) {
        return String.format("%.6f", x);
    }

    private void createDir(final Path path) {
        if (path.getParent() != null) {
            try {
                Files.createDirectories(path.getParent());
            } catch (final IOException e) {
                System.err.println("Exception while creating file: " + e.getMessage());
            }
        }
    }
}
