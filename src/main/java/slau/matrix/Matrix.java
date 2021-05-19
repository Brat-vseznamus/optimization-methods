package slau.matrix;

import java.util.stream.IntStream;

public interface Matrix {
    double DOUBLE_EQUALS_EPS = 0.0001;

    int getN();

    int getM();

    double get(int row, int col);

    void set(int row, int col, double value);

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
}
