package methods.dimensional.poly;

import java.util.function.BinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.*;

public class DoubleVector {
    private final int n;
    private final Double[] values;
    public DoubleVector(final int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Size of vector should be > 0!!!");
        }
        this.n = n;
        values =  new Double[n];
        IntStream.range(0, n).forEach(i -> values[i] = 0d);
    }

    public DoubleVector(final Double...doubles) {
        n = doubles.length;
        values = doubles.clone();
    }

    public DoubleVector(final DoubleVector vector, final int size) {
        n = size;
        values = Arrays.copyOf(vector.values, size);
    }

    public DoubleVector(final double[] doubles) {
        n = doubles.length;
        values = Arrays.stream(doubles).boxed().collect(Collectors.toList()).toArray(new Double[n]);
    }

    public DoubleVector(final double[] doubles, final int size) {
        n = size;
        values = Arrays.stream(doubles).boxed().collect(Collectors.toList()).toArray(new Double[n]);
    }

    private DoubleVector elementOperation(final DoubleVector vector, final BinaryOperator<Double> function) {
        if (vector.n != n) {
            throw new IllegalArgumentException("DoubleVectors should be same sizes.");
        }
        return new DoubleVector((IntStream.range(0, n).mapToDouble(i -> function.apply(values[i], vector.values[i])).toArray()));
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

    public Double get(final int i) {
        if (i < 0 || i >= n) {
            throw new IndexOutOfBoundsException("Index should be in range [0, " + n + ").");
        }
        return values[i];
    }

    public void set(final int i, final Double v) {
        if (i < 0 || i >= n) {
            throw new IndexOutOfBoundsException("Index should be in range [0, " + n + ").");
        }
        values[i] = v;
    }

    public int size() {
        return n;
    }

    @Override
    public String toString() {
        return Arrays.toString(values);
    }

    public DoubleVector multiply(final Matrix matrix) {
        if (n != matrix.getN()) {
            throw new IllegalArgumentException("cringe");
        }
        return  matrix.transpose().multiply(this);
    }

    public Stream<Double> stream() {
        return Arrays.stream(values);
    }
}
