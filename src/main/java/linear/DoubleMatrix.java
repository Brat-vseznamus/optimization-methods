package linear;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class DoubleMatrix extends AbstractMatrix {
    protected final int n, m;
    protected final List<DoubleVector> values;

    public DoubleMatrix(final DoubleVector... rows) {
        Vectors.requireEqualSizes(rows);

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

    public DoubleMatrix(final double[]... data) {
        this((DoubleVector[]) Arrays.stream(data).map(DoubleVector::new).toArray());
    }

    public DoubleMatrix(final int n, final int m) {
        this((DoubleVector[]) IntStream.range(0, n).mapToObj(i -> new DoubleVector(m)).toArray());
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

    @Override
    public String toString() {
        return Arrays.deepToString(values.toArray()).replace("],", String.format("],%n"));
    }

    public boolean isDiagonal() {
        return false;
    }

    public Stream<DoubleVector> stream() {
        return values.stream();
    }
}
