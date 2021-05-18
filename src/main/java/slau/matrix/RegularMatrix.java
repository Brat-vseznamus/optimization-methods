package slau.matrix; 

import java.util.Arrays;
import java.util.stream.IntStream;

import slau.matrix.Matrix;

public class RegularMatrix implements Matrix {
    private double[][] data;
    private final int n, m;

    public RegularMatrix(int n, int m) {
        this.n = n;
        this.m = m;
    }

    public RegularMatrix(double[][] data) {
        this(data.length, data[0].length);
        this.data = data;
        int n = data.length;
        int m = data[0].length;
        if (!IntStream.range(1, n).allMatch(i -> data[i].length == m)) {
            throw new IllegalArgumentException("all rows should have same size");
        }
    }

    @Override
    public double get(int row, int col) {
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
    public void set(int row, int col, double value) {
        if (!validIndexes(row, col)) {
            throw new IllegalArgumentException(
                "illegal row or column:[" + row + ", " + col + "]");
        }
        data[row][col] = value;
    }

    protected boolean validIndexes(int row, int col) {
        return 0 <= row && row < n 
            && 0 <= col && col < m;
    }

    
}
