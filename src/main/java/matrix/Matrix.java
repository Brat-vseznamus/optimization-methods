package matrix;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public interface Matrix {
    double DOUBLE_EQUALS_EPS = 0.0001;

    int getN();

    int getM();

    default DoubleVector get(final int row) {
        final DoubleVector result = new DoubleVector(getN());
        IntStream.range(0, getM()).forEach(col -> result.set(col, get(row, col)));
        return result;
    }

    double get(int row, int col);

    void set(int row, int col, double value);

    boolean isDiagonal();

    default void swapRows(final int row1, final int row2) {
        final int m = getM();
        IntStream.range(0, m)
                .forEach(col -> {
                    final double oldRow1Value = get(row1, col);
                    set(row1, col, get(row2, col));
                    set(row2, col, oldRow1Value);
                });
    }

    default boolean equals(final Matrix matrix) {
        if (matrix.getM() != getM() ||
                matrix.getN() != getN()) {
            return false;
        }
        for (int row = 0; row < getM(); row++) {
            for (int col = 0; col < getN(); col++) {
                if (Math.abs(matrix.get(row, col) - get(row, col)) > DOUBLE_EQUALS_EPS) {
                    return false;
                }
            }
        }
        return true;
    }

    default Matrix multiply(final Matrix matrix) {
        if (this.getM() != matrix.getN()) {
            throw new IllegalArgumentException("illegal sizes of matrices");
        }
        final double[][] res = new double[this.getN()][matrix.getM()];
        for (int row = 0; row < this.getN(); row++) {
            for (int col = 0; col < matrix.getM(); col++) {
                for (int cell = 0; cell < this.getM(); cell++) {
                    res[row][col] += this.get(row, cell) * matrix.get(cell, col);
                }
            }
        }
        return new RegularMatrix(res);
    }

    default String output() {
        return IntStream.range(0, getN())
                .mapToObj(i -> IntStream.range(0, getM())
                        .mapToObj(j -> String.format("%.5f", get(i, j)))
                        .collect(Collectors.joining(", ", "[", "]")))
                .collect(Collectors.joining(String.format(",%n"), "[", "]"));
    }

    default double[] multiplyBy(final double[] vector) {
        if (vector.length != this.getM()) {
            throw new IllegalArgumentException("illegal vector size");
        }

        final double[] answer = new double[vector.length];
        for (int row = 0; row < getN(); row++) {
            for (int col = 0; col < getM(); col++) {
                answer[row] += vector[col] * get(row, col);
            }
        }
        return answer;
    }

    default DoubleVector multiplyBy(final DoubleVector vector) {
        return new DoubleVector(multiplyBy(vector.toArray()));
    }
}
