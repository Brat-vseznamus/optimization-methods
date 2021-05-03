package methods.dimensional.poly;

import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DiagonalMatrix extends Matrix {

    public DiagonalMatrix(DoubleVector diag) {
        n = m = diag.size();
        values = new ArrayList<>();
        values.add(diag);
    }

    @Override
    public Double get(int i, int j) {
        return i == j ? values.get(0).get(i) : 0d;
    }

    @Override
    public DoubleVector get(int i) {
        final DoubleVector row = new DoubleVector(n);
        row.set(i, get(i, i));
        return row;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        IntStream.range(0, n).forEach(
            i -> {
                sb.append("[");
                IntStream.range(0, m).forEach(
                    j -> {
                        if (j == 0) {
                            sb.append(get(i, j).toString());
                        } else {
                            sb.append(", ").append(get(i, j).toString());
                        }
                    }
                );
                sb.append("]\n");
            }
        );
        return sb.toString();
    }

    @Override
    public boolean isDiagonal() {
        return true;
    }

    @Override
    public DoubleVector multiply(DoubleVector vector) {
        if (m != vector.size()) {
            throw new IllegalArgumentException("Wide and height should be same.");
        }
        final DoubleVector v = new DoubleVector(n);
        IntStream.range(0, n).forEach(i -> v.set(i, get(i, i) * vector.get(i)));
        return v;
    }

    @Override
    public void set(int i, int j, double val) {
        if (i == j) {
            values.get(0).set(i, val);
        } else {
            throw new IllegalArgumentException("Illegal operation");
        }
    }

    @Override
    public void set(int i, DoubleVector val) {
        values.get(0).set(i, val.get(i));
    }

    @Override
    public Stream<DoubleVector> stream() {
        return super.stream();
    }

    @Override
    public Matrix transpose() {    
        return this;
    }
    
}