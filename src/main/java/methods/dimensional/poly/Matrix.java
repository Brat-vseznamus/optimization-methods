package methods.dimensional.poly;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Matrix {
    private final boolean diagonal;
    private final List<DoubleVector> values;
    private final int n, m;

    public Matrix(final int n, final int m, final boolean diagonal) {
        this.n = n;
        this.m = m;
        values = new ArrayList<>(Collections.nCopies(n, new DoubleVector(m)));
        for (int i = 0; i < n; i++) {
            values.set(i, new DoubleVector(m));
        }
        this.diagonal = diagonal;
        if (diagonal && n != m) {
            throw new IllegalArgumentException("Diagonal matrix must be same width and height (n, m)");
        }
    }

    public Matrix(final int n, final int m) {
        this(n, m, false);
    }

    public Matrix(final DoubleVector... rows) {
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
        diagonal = false;
    }

    public Matrix(final double[][] matrix, final boolean diagonal) {
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
        this.diagonal = diagonal;
        if (diagonal && n != m) {
            throw new IllegalArgumentException("Diagonal matrix must be same width and height (n, m)");
        }
        if (diagonal && !IntStream.range(0, n)
                            .allMatch(i -> IntStream.range(0, n)
                                .allMatch(j -> i == j || matrix[i][j] == 0))) {
            throw new IllegalArgumentException("Diagonal matrix must be diagonal");
        }
    }

    public Matrix(final DoubleVector diag) {
        n = m = diag.size();
        values = new ArrayList<>();
        diagonal = true;
        values.add(diag);
    }

    public Matrix(final double[][] matrix) {
        this(matrix, false);
    }

    public DoubleVector multiply(final DoubleVector vector) {
        if (m != vector.size()) {
            throw new IllegalArgumentException("Wide and height should be same.");
        }
        if (diagonal) {
            final DoubleVector v = new DoubleVector(n);
            IntStream.range(0, n).forEach(i -> v.set(i, get(i, i) * vector.get(i)));
            return v;
        }
        return new DoubleVector(values.stream().mapToDouble(v -> v.scalar(vector)).toArray());
    }

    public int getN() {
        return n;
    }

    public int getM() {
        return m;
    }

    public Double get(final int i, final int j) {
        if (diagonal) {
            return i == j ? values.get(0).get(i) : 0d;
        }
        return values.get(i).get(j);
    }

    public DoubleVector get(final int i) {
        if (diagonal) {
            final DoubleVector row = new DoubleVector(n);
            row.set(i, get(i, i));
            return row;
        }
        return values.get(i);
    }

    public void set(final int i, final int j, final double val) {
        if (diagonal && i == j) {
            values.get(0).set(i, val);
            return;
        } else if (diagonal) {
            throw new IllegalArgumentException("Illegal operation");
        }
        values.get(i).set(j, val);
    }

    public void set(final int i, final DoubleVector val) {
        if (diagonal) {
            values.get(0).set(i, val.get(i));
            return;
        }
        values.set(i, val);
    }

    public List<DoubleVector> getValues() {
        return values;
    }

    public Matrix transpose() {
        if (diagonal) {
            return this;
        }
        final Matrix mat = new Matrix(m, n);
        IntStream.range(0, n).forEach(
            i -> IntStream.range(0, m).forEach(
                j -> mat.values.get(j).set(i, get(i, j))
            )
        );
        return mat;
    }

    @Override
    public String toString() {
        if (isDiagonal()) {
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
        return Arrays.deepToString(values.toArray()).replace("],", String.format("],%n"));
    }

    public Stream<DoubleVector> stream() {
        return values.stream();
    }

    public boolean isDiagonal() {
        return diagonal;
    }
}
