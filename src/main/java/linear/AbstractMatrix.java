package linear;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public abstract class AbstractMatrix implements Matrix {
    @Override
    public void swapRows(final int row1, final int row2) {
        final int m = getM();
        IntStream.range(0, m)
                .forEach(col -> {
                    final double oldRow1Value = get(row1, col);
                    set(row1, col, get(row2, col));
                    set(row2, col, oldRow1Value);
                });
    }

    @Override
    public boolean equals(final Matrix matrix) {
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

    @Override
    public DoubleVector get(final int row) {
        final DoubleVector result = new DoubleVector(getN());
        IntStream.range(0, getM()).forEach(col -> result.set(col, get(row, col)));
        return result;
    }

    @Override
    public Matrix multiply(final Matrix matrix) {
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

    @Override
    public Matrix add(final Matrix other) {
        final Matrix result = new DoubleMatrix(getN(), getM());

        IntStream.range(0, getN()).forEach(row ->
                IntStream.range(0, getN()).forEach(col ->
                        result.set(row, col, get(row, col) + other.get(row, col))
                )
        );

        return result;
    }

    @Override
    public Matrix subtract(final Matrix other) {
        final Matrix result = new DoubleMatrix(getN(), getM());

        IntStream.range(0, getN()).forEach(row ->
                IntStream.range(0, getN()).forEach(col ->
                        result.set(row, col, get(row, col) - other.get(row, col))
                )
        );

        return result;
    }

    @Override
    public double[] multiplyBy(final double[] vector) {
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

    @Override
    public DoubleVector multiplyBy(final DoubleVector vector) {
        return new DoubleVector(multiplyBy(vector.toArray()));
    }

    protected boolean invalid(final int row, final int col) {
        return 0 > row || row >= getN() || 0 > col || col >= getM();
    }

    @Override
    public double minor(int row, int col, int delta) {
        if (invalid(row, col) || invalid(row + delta - 1, col + delta - 1)) {
            throw new IllegalArgumentException("row, col and row + delta, col + delta must be in matrix bounds");
        }
        if (delta < 1) {
            throw new IllegalArgumentException("delta must be >= 1");
        }
        if (delta == 1) {
            return get(row, col);
        }

        // first column decomposition
        double result = 0d;
        final Matrix tempMinor = new RegularMatrix(delta - 1, delta - 1);
        for (int i = row; i < row + delta; i++) {
            int tempRow = -1;
            for (int r = row; r < row + delta; r++) {
                if (r == i) {
                    continue;
                }
                tempRow++;
                int tempCol = -1;
                for (int c = col + 1; c < col + delta; c++) {
                    tempCol++;
                    tempMinor.set(tempRow, tempCol, get(r, c));
                }
            }

            // recursive determinant
            double coef = (i + col) % 2 == 0 ? 1 : -1;
            result += coef * get(i, col) * tempMinor.minor(0, 0, delta - 1);
        }
        return result;
    }

    @Override
    public double determinant() {
        Matrices.requireSquare(this);

        return minor(0, 0, getN());
    }

    @Override
    public String toString() {
        return IntStream.range(0, getN())
                .mapToObj(i -> IntStream.range(0, getM())
                        .mapToObj(j -> String.format("%.5f", get(i, j)))
                        .collect(Collectors.joining(", ", "[", "]")))
                .collect(Collectors.joining(String.format(",%n"), "[", "]"));
    }
}
