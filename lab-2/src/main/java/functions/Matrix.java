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

import functions.DoubleVector;

public class Matrix {
    private List<DoubleVector> values;
    private int n, m;

    public Matrix(int n, int m) {
        this.n = n;
        this.m = m;
        values = new ArrayList<>(Collections.nCopies(n, new DoubleVector(m)));
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

}
