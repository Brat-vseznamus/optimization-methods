package linear;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.IntStream;

public class Matrices {
    public static final double EPS = 1e-3d;

    private Matrices() {
    }

    public static DoubleMatrix E(final int n) {
        final double[][] data = new double[n][n];
        for (int i = 0; i < n; i++) {
            data[i][i] = 1;
        }
        return new DoubleMatrix(data);
    }

    public static boolean epsEquals(final double a, final double b) {
        return Math.abs(a - b) < EPS;
    }

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

    // SHAPE OF MATRIX CHECK
    public static boolean checkShape(final double[][] data) {
        requireNonNullComponents(data);

        return Arrays.stream(data).allMatch((row) -> row.length == data[0].length);
    }

    public static void requireShape(final double[][] data) {
        if (!checkShape(data)) {
            throw new IllegalArgumentException("data rows must have same length");
        }
    }

    // SQUARE CHECK
    public static boolean checkSquare(final Matrix matrix) {
        requireNonNullComponents(matrix);

        return matrix.getN() == matrix.getM();
    }

    public static boolean checkSquare(final double[][] data) {
        requireShape(data);

        return Arrays.stream(data).allMatch((row) -> row.length == data.length);
    }

    public static void requireSquare(final Matrix matrix) {
        if (!checkSquare(matrix)) {
            throw new IllegalArgumentException("matrix must have same dimensions");
        }
    }

    public static void requireSquare(final double[][] data) {
        if (!checkSquare(data)) {
            throw new IllegalArgumentException("data must have same dimensions");
        }
    }

    // SYMMETRIC CHECK
    public static boolean checkSymmetric(final Matrix matrix) {
        requireNonNullComponents(matrix);

        return checkSquare(matrix) && IntStream.range(0, matrix.getN())
                .anyMatch(row -> IntStream.range(0, row).allMatch(col ->
                        matrix.get(row, col) == matrix.get(col, row)));
    }

    public static boolean checkSymmetric(final double[][] data) {
        requireShape(data);

        return checkSymmetric(new RegularMatrix(data));
    }

    public static void requireSymmetric(final Matrix matrix) {
        if (!checkSymmetric(matrix)) {
            throw new IllegalArgumentException("matrix must be symmetric");
        }
    }

    public static void requireSymmetric(final double[][] data) {
        if (!checkSymmetric(data)) {
            throw new IllegalArgumentException("matrix must be symmetric");
        }
    }

    public static boolean checkPositive(final Matrix matrix) {
        requireSquare(matrix);

        return IntStream.range(1, matrix.getN()).allMatch(k -> matrix.minor(0, 0, k) > 0);
    }

    public static void requirePositive(final Matrix matrix) {
        if (!checkPositive(matrix)) {
            throw new IllegalArgumentException("Matrix must be positively determined");
        }
    }
}
