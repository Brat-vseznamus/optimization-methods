package slau.matrix; 

import java.util.Arrays;
import java.util.stream.IntStream;

import slau.matrix.Matrix;

public class RegularMatrix implements Matrix {
    protected double[][] data;
    protected final int n, m;

    public RegularMatrix(final int n, final int m) {
        this.n = n;
        this.m = m;
        data = new double[n][m];
    }

    public RegularMatrix(final double[][] data) {
        this(data.length, data[0].length);
        this.data = data;
        final int n = data.length;
        final int m = data[0].length;
        if (!IntStream.range(1, n).allMatch(i -> data[i].length == m)) {
            throw new IllegalArgumentException("all rows should have same size");
        }
    }

    @Override
    public double get(final int row, final int col) {
        if (!validIndexes(row, col)) {
            throw new IllegalArgumentException(
                "illegal row or column:[" + row + ", " + col + "]");
        }
        return data[row][col];
    }

    @Override
    public int getN() {
        return n;
    }

    @Override
    public int getM() {
        return m;
    }

    @Override
    public void set(final int row, final int col, final double value) {
        if (!validIndexes(row, col)) {
            throw new IllegalArgumentException(
                "illegal row or column:[" + row + ", " + col + "]");
        }
        data[row][col] = value;
    }

    protected boolean validIndexes(final int row, final int col) {
        return 0 <= row && row < n 
            && 0 <= col && col < m;
    }

    @Override
    public String toString() {
        return Arrays.deepToString(data)
                .replace("],", "],\n");
    }


}
