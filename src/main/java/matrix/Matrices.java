package matrix;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.IntStream;

public class Matrices {

    public static void requireNonNullComponents(final Matrix matrix) {
        if (matrix == null || IntStream.range(0, matrix.getN())
                .mapToObj(matrix::get).anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException("matrix and every matrix row must be non null");
        }
    }

    public static void requireNonNullComponents(final double[][] data) {
        if (data == null || Arrays.stream(data).anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException("data and every data[i] must be non null");
        }
    }

    public static boolean checkSquare(final Matrix matrix) {
        return matrix.getN() == matrix.getM();
    }

    public static boolean checkSquare(final double[][] data) {
        return Arrays.stream(data).allMatch((row) -> row.length == data.length);
    }

    public static void requireSquare(final Matrix matrix) {
        requireNonNullComponents(matrix);
        if (checkSquare(matrix)) {
            throw new IllegalArgumentException("matrix must have same dimensions");
        }
    }

    public static void requireSquare(final double[][] data) {
        requireNonNullComponents(data);
        if (!checkSquare(data)) {
            throw new IllegalArgumentException("matrix must have same dimensions");
        }
    }

    public static boolean checkSymmetric(final Matrix matrix) {
        requireNonNullComponents(matrix);

        return checkSquare(matrix) && IntStream.range(0, matrix.getN())
                .anyMatch(row -> IntStream.range(0, row).allMatch(col ->
                        matrix.get(row, col) == matrix.get(col, row)));
    }
}
