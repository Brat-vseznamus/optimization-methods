package matrix;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class DoubleMatrix implements Matrix {
    protected List<DoubleVector> values;
    protected int n, m;
    
    public DoubleMatrix(final int n, final int m) {
        this.n = n;
        this.m = m;
        values = new ArrayList<>(Collections.nCopies(n, new DoubleVector(m)));
        for (int i = 0; i < n; i++) {
            values.set(i, new DoubleVector(m));
        }
    }

    public DoubleMatrix(final DoubleVector... rows) {
        n = rows.length;
        if (Arrays.stream(rows).anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException("Rows have null vectors.");
        }
        final int maxSize = Arrays.stream(rows).map(DoubleVector::size).max(Comparator.naturalOrder()).orElse(0);
        m = maxSize;
        values = new ArrayList<>(Collections.nCopies(n, new DoubleVector(maxSize)));
        IntStream.range(0, n).forEach(
                i -> values.set(i, new DoubleVector(rows[i], maxSize))
        );
    }

    public DoubleMatrix(final double[][] matrix) {
        n = matrix.length;
        if (Arrays.stream(matrix).anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException("Rows have null vectors.");
        }
        final int maxSize = Arrays.stream(matrix).map(v -> v.length).max(Comparator.naturalOrder()).orElse(0);
        m = maxSize;
        values = new ArrayList<>(Collections.nCopies(n, new DoubleVector(maxSize)));
        IntStream.range(0, n).forEach(
                i -> values.set(i, new DoubleVector(matrix[i], maxSize))
        );
    }

    public DoubleVector multiply(final DoubleVector vector) {
        if (m != vector.size()) {
            throw new IllegalArgumentException("Wide and height should be same.");
        }
        return new DoubleVector(values.stream().mapToDouble(v -> v.scalar(vector)).toArray());
    }

    public int getN() {
        return n;
    }

    public int getM() {
        return m;
    }

    public double get(final int i, final int j) {
        return values.get(i).get(j);
    }

    public DoubleVector get(final int i) {
        return values.get(i);
    }

    public void set(final int i, final int j, final double val) {
        values.get(i).set(j, val);
    }

    public void set(final int i, final DoubleVector val) {
        values.set(i, val);
    }

    public List<DoubleVector> getValues() {
        return values;
    }

    public DoubleMatrix transpose() {
        final DoubleMatrix mat = new DoubleMatrix(m, n);
        IntStream.range(0, n).forEach(
            i -> IntStream.range(0, m).forEach(
                j -> mat.set(j, i, get(i, j))
            )
        );
        return mat;
    }

    @Override
    public String toString() {
        return Arrays.deepToString(values.toArray()).replace("],", String.format("],%n"));
    }

    public Stream<DoubleVector> stream() {
        return values.stream();
    }

    public boolean isDiagonal() {
        return false;
    }
}
