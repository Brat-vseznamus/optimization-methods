package methods.dimensional.poly;

import java.util.function.BinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.*;

public class DoubleVector {
    private final Double[] values;  // TODO replace with double[]

    public DoubleVector(final int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Size of vector must be >= 0");
        }
        values = new Double[n];
        IntStream.range(0, n).forEach(i -> values[i] = 0d);
    }

    public DoubleVector(final Double... doubles) {
        values = doubles.clone();
    }

    public DoubleVector(final DoubleVector vector, final int size) {
        values = Arrays.copyOf(vector.values, size);
    }

    public DoubleVector(final double[] doubles) {
        // values = doubles;
        values = new Double[doubles.length];
        for (int i = 0; i < doubles.length; i++) {
            values[i] = doubles[i];
        }
    }

    public DoubleVector(final double[] doubles, final int size) {
        values = Arrays.stream(doubles).boxed().collect(Collectors.toList()).toArray(new Double[size]);
    }

    private DoubleVector elementOperation(final DoubleVector vector, final BinaryOperator<Double> function) {
        if (values.length != vector.values.length) {
            throw new IllegalArgumentException("DoubleVectors should be same sizes.");
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

    public double scalar(final DoubleVector vector) {
        return Arrays.stream(elementOperation(vector, (x, y) -> x * y).values)
                .reduce(0d, Double::sum);
    }

    public double norm() {
        return Math.sqrt(scalar(this));
    }

    private void requireIndexInRange(int i) {
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

    @Override
    public String toString() {
        return Arrays.toString(values);
    }

    public DoubleVector multiply(final Matrix matrix) {
        if (values.length != matrix.getN()) {
            throw new IllegalArgumentException("cringe");
        }
        return matrix.transpose().multiply(this);
    }

    public Stream<Double> stream() {
        return Arrays.stream(values);
    }
}
