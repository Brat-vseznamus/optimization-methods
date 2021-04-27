package functions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Vector;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import functions.DoubleVector;

public class Matrix {
    private List<DoubleVector> values;
    private int n, m;

    public Matrix(int n, int m) {
        this.n = n;
        this.m = m;
        values = new ArrayList<>(Collections.nCopies(n, new DoubleVector(m)));
        for (int i = 0; i < n; i++) {
            values.set(i, new DoubleVector(m));
        }
    }

    public Matrix(DoubleVector...rows) {
        n = rows.length;
        if (Arrays.stream(rows).anyMatch(v -> Objects.isNull(v))) {
            throw new IllegalArgumentException("Rows have null vectors.");
        }
        int maxSize = Arrays.stream(rows).map(DoubleVector::size).max(Comparator.naturalOrder()).orElse(0);
        m = maxSize;
        values = new ArrayList<>(Collections.nCopies(n, new DoubleVector(maxSize)));
        IntStream.range(0, n).forEach(
            i -> values.set(i, new DoubleVector(rows[i], maxSize))
        );
    }

    public Matrix(double[][] matrix) {
        n = matrix.length;
        if (Arrays.stream(matrix).anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException("Rows have null vectors.");
        }
        int maxSize = Arrays.stream(matrix).map(v -> v.length).max(Comparator.naturalOrder()).orElse(0);
        m = maxSize;
        values = new ArrayList<>(Collections.nCopies(n, new DoubleVector(maxSize)));
        IntStream.range(0, n).forEach(
            i -> values.set(i, new DoubleVector(matrix[i], maxSize))
        );
    }

    public DoubleVector multiply(DoubleVector vector) {
        if (m != vector.size()) {
            throw new IllegalArgumentException("Wide and height should be save.");
        }
        return new DoubleVector(values.stream().mapToDouble(v -> v.scalar(vector)).toArray());
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public int getM() {
        return m;
    }

    public void setM(int m) {
        this.m = m;
    }

    public Double get(int i, int j) {
        return values.get(i).get(j);
    }

    public DoubleVector get(int i) {
        return values.get(i);
    }

    public List<DoubleVector> getValues() {
        return values;
    }

    public Matrix transpose() {
        Matrix mat = new Matrix(m, n);
        IntStream.range(0, n).forEach(
            i -> {
                IntStream.range(0, m).forEach(
                    j -> {
                        mat.values.get(j).set(i, get(i, j));
                    }
                );
            }
        );
        return mat;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return Arrays.deepToString(values.toArray()).replace("],", "],\n");
    }

    public Stream<DoubleVector> stream() {
        return values.stream();
    }

}
