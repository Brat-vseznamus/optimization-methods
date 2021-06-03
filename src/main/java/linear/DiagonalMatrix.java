package linear;

import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.ArrayList;

public class DiagonalMatrix extends AbstractMatrix {
    private final int n;
    private final DoubleVector diagonal;

    public DiagonalMatrix(final DoubleVector diagonal) {
        Vectors.requireNonNull(diagonal);

        n = diagonal.size();
        this.diagonal = diagonal;
    }

    @Override
    public int getN() {
        return n;
    }

    @Override
    public int getM() {
        return n;
    }

    @Override
    public double get(final int i, final int j) {
        return i == j ? diagonal.get(i) : 0d;
    }

    @Override
    public DoubleVector get(final int i) {
        final DoubleVector row = new DoubleVector(n);
        row.set(i, get(i, i));
        return row;
    }

    @Override
    public void set(final int i, final int j, final double val) {
        if (i == j) {
            diagonal.set(i, val);
        } else {
            throw new IllegalArgumentException("Can't set out of the main diagonal in DiagonalMatrix");
        }
    }

    @Override
    public boolean isDiagonal() {
        return true;
    }

    @Override
    public DoubleVector multiplyBy(final DoubleVector vector) {
        if (n != vector.size()) {
            throw new IllegalArgumentException("Wide and height must be same.");
        }
        final DoubleVector v = new DoubleVector(n);
        IntStream.range(0, n).forEach(i -> v.set(i, get(i, i) * vector.get(i)));
        return v;
    }

    public void set(final int i, final DoubleVector val) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Matrix transpose() {
        return this;
    }

}