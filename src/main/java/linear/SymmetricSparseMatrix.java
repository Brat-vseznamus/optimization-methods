package linear;

import java.util.stream.IntStream;

public class SymmetricSparseMatrix extends AbstractMatrix {
    private final int n;
    private final double[] diagonal;
    private final double[] rowElements;
    private final int[] columns;
    private final int[] beginIndex;

    public SymmetricSparseMatrix(final double[][] data) {
        Matrices.requireSquare(data);

        n = data.length;
        diagonal = new double[n];
        IntStream.range(0, n).forEach(i -> diagonal[i] = data[i][i]);

        final int size = IntStream.range(0, n).map(i ->
                IntStream.range(0, i).map(j -> Matrices.epsEquals(data[i][j], 0d) ? 0 : 1).sum()
        ).sum();
        rowElements = new double[size];
        columns = new int[size];
        beginIndex = new int[n + 1];

        int index = 0;
        for (int i = 0; i < n; ++i) {
            beginIndex[i] = index;
            for (int j = 0; j < i; ++j) {
                final double element = data[i][j];
                if (!Matrices.epsEquals(element, 0d)) {
                    rowElements[index] = element;
                    columns[index++] = j;
                }
            }
        }
        beginIndex[n] = size;
    }

    @Override
    public int getN() {
        return n;
    }

    @Override
    public int getM() {
        return n;
    }

    private int countRow(final int row) {
        return beginIndex[row + 1] - beginIndex[row];
    }

    @Override
    public double get(int row, int col) {
        if (row == col) {
            return diagonal[row];
        }
        if (row < col) {
            // swap(row, col)
            row += col;
            col = row - col;
            row -= col;
        }
        if (countRow(row) == 0) {
            return 0d;
        }
        // binary search
        int left = beginIndex[row] - 1;
        int right = beginIndex[row + 1];
        while (left + 1 < right) {
            final int mid = (left + right) / 2;
            if (columns[mid] <= col) {
                left = mid;
            } else {
                right = mid;
            }
        }
        if (left == beginIndex[row] - 1 || columns[left] != col) {
            return 0d;
        }
        return rowElements[left];
    }

    @Override
    public void set(final int row, final int col, final double value) {
        int index;
        for (index = beginIndex[row]; index < beginIndex[row + 1] && columns[index] <= col; ++index) {
            if (index == columns[index]) {
                rowElements[index] = value;
            }
        }
    }

    @Override
    public boolean isDiagonal() {
        return false;
    }
}
