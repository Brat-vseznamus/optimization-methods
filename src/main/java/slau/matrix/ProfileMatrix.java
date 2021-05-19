package slau.matrix;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class ProfileMatrix implements Matrix {
    private final int n;
    private final double[] diagonal;
    private final double[] rowElements;
    private final double[] colElements;
    private final int[] rowBeginIndices;
    private final int[] colBeginIndices;

    public ProfileMatrix(final double[][] data) {
        // TODO quadratic check
        n = data.length;
        diagonal = new double[n];
        IntStream.range(0, n).forEach(i -> diagonal[i] = data[i][i]);

        rowBeginIndices = new int[n + 1];
        colBeginIndices = new int[n + 1];

        final List<Double> rowElementsList = new ArrayList<>();
        for (int row = 0; row < n; row++) {
            rowBeginIndices[row] = rowElementsList.size();
            boolean wasNonZero = false;
            for (int col = 0; col < row; col++) {
                // TODO think about (|data[row][col]| < eps)
                if (data[row][col] != 0) {
                    wasNonZero = true;
                }

                if (wasNonZero) {
                    rowElementsList.add(data[row][col]);
                }
            }
        }
        rowBeginIndices[n] = rowElementsList.size();
        rowElements = rowElementsList.stream().mapToDouble((el) -> el).toArray();

        final List<Double> colElementsList = new ArrayList<>();
        for (int col = 0; col < n; col++) {
            colBeginIndices[col] = colElementsList.size();
            boolean wasNonZero = false;
            for (int row = 0; row < col; row++) {
                // TODO think about (|data[row][col]| < eps)
                if (data[row][col] != 0) {
                    wasNonZero = true;
                }

                if (wasNonZero) {
                    colElementsList.add(data[row][col]);
                }
            }
        }
        colBeginIndices[n] = colElementsList.size();
        colElements = colElementsList.stream().mapToDouble((el) -> el).toArray();
    }

    @Override
    public int getN() {
        return n;
    }

    @Override
    public int getM() {
        return n;
    }

    private int rowCount(final int row) {
        return rowBeginIndices[row + 1] - rowBeginIndices[row];
    }

    private int colCount(final int col) {
        return colBeginIndices[col + 1] - colBeginIndices[col];
    }

    private int rowToBeginCol(final int row) {
        return row - rowCount(row);
    }

    private int colToBeginRow(final int col) {
        return col - colCount(col);
    }

    private boolean outOfProfile(final int row, final int col) {
        return row != col
                && (row < col && colCount(col) < col - row
                || rowCount(row) < row - col);
    }

    private int getColProfileIndex(final int row, final int col) {
        return colBeginIndices[col] + row - colToBeginRow(col);
    }

    private int getRowProfileIndex(final int row, final int col) {
        return rowBeginIndices[row] + col - rowToBeginCol(row);
    }

    @Override
    public double get(final int row, final int col) {
        if (outOfProfile(row, col)) {
            return 0d;
        }

        if (row == col) {
            return diagonal[row];
        }
        if (row < col) {
            return colElements[getColProfileIndex(row, col)];
        } else {
            return rowElements[getRowProfileIndex(row, col)];
        }
    }

    @Override
    public void set(final int row, final int col, final double value) {
        if (outOfProfile(row, col)) {
            throw new UnsupportedOperationException("row and col must be in profile of matrix");
        }

        if (row == col) {
            diagonal[row] = value;
        }
        if (row < col) {
            colElements[getColProfileIndex(row, col)] = value;
        } else {
            rowElements[getRowProfileIndex(row, col)] = value;
        }
    }
}
