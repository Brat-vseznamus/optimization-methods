package linear;

import java.util.function.BinaryOperator;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.*;

public class DoubleVector {
    private final double[] values;

    public DoubleVector(final int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Size of vector must be >= 0");
        }
        values = new double[n];
    }

    public DoubleVector(final DoubleVector vector, final int size) {
        values = Arrays.copyOf(vector.values, size);
    }

    public DoubleVector(final double... doubles) {
        values = new double[doubles.length];
        System.arraycopy(doubles, 0, values, 0, doubles.length);
    }

    public DoubleVector(final double[] doubles, final int size) {
        values = Arrays.copyOf(doubles, size);
    }

    private DoubleVector elementOperation(final DoubleVector vector, final BinaryOperator<Double> function) {
        if (values.length != vector.values.length) {
            throw new IllegalArgumentException("DoubleVectors must have same sizes.");
        }
        return new DoubleVector((IntStream.range(0, values.length).mapToDouble(i -> function.apply(values[i], vector.values[i])).toArray()));
    }

    public DoubleVector add(final DoubleVector vector) {
        return elementOperation(vector, Double::sum);
    }

    public DoubleVector subtract(final DoubleVector vector) {
        return elementOperation(vector, (x, y) -> x - y);
    }

    public DoubleVector multiplyBy(final double k) {
        return elementOperation(this, (x, v) -> k * x);
    }

    public DoubleMatrix multiply(final DoubleVector vc) {
        final double[][] data = new double[size()][vc.size()];
        for (int i = 0; i < size(); i++) {
            for (int j = 0; j < vc.size(); j++) {
                data[i][j] = get(i) * vc.get(j);
            }
        }
        return new DoubleMatrix(data);
    }

    public double scalar(final DoubleVector vector) {
        return Arrays.stream(elementOperation(vector, (x, y) -> x * y).values)
                .reduce(0d, Double::sum);
    }

    public double norm() {
        return Math.sqrt(scalar(this));
    }

    private void requireIndexInRange(final int i) {
        if (i < 0 || i >= values.length) {
            throw new IndexOutOfBoundsException("Index is out of range [0, " + values.length + ").");
        }
    }

    public Double get(final int i) {
        requireIndexInRange(i);
        return values[i];
    }

    public void set(final int i, final Double v) {
        requireIndexInRange(i);
        values[i] = v;
    }

    public int size() {
        return values.length;
    }

    public DoubleVector multiply(final DoubleMatrix matrix) {
        if (values.length != matrix.getN()) {
            throw new IllegalArgumentException("cringe");
        }

        return IntStream.range(0, values.length).mapToObj(i -> matrix.get(i).multiplyBy(values[i]))
                .reduce(DoubleVector::add).orElseGet(() -> new DoubleVector(values.length));
    }

    public DoubleStream stream() {
        return Arrays.stream(values);
    }

    public double[] toArray() {
        return values;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof DoubleVector) {

            final DoubleVector other = (DoubleVector) obj;
            return size() == other.size()
                    && IntStream.range(0, size()).allMatch(i -> Matrices.epsEquals(get(i), other.get(i)));
        }
        return false;
    }

    @Override
    public String toString() {
        return Arrays.toString(values);
    }
}
