package functions;

import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.*;

import functions.Matrix;

public class DoubleVector {
    private int n;
    private Double[] values;
    public DoubleVector(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Size of vector should be > 0!!!");
        }
        this.n = n;
        values = new Double[n];
        Arrays.fill(values, 0d);
    }

    public DoubleVector(Double...doubles) {
        n = doubles.length;
        values = doubles.clone();
    }

    public DoubleVector(DoubleVector vector, int size) {
        n = size;
        values = Arrays.copyOf(vector.values, size);
    }

    public DoubleVector(double[] doubles) {
        n = doubles.length;
        values = Arrays.stream(doubles).boxed().collect(Collectors.toList()).toArray(new Double[n]);
    }

    public DoubleVector(double[] doubles, int size) {
        n = size;
        values = Arrays.stream(doubles).boxed().collect(Collectors.toList()).toArray(new Double[n]);
    }

    private DoubleVector elementOperation(DoubleVector vector, BinaryOperator<Double> function) {
        if (vector.n != n) {
            throw new IllegalArgumentException("DoubleVectors should be same sizes.");
        }
        return new DoubleVector((IntStream.range(0, n).mapToDouble(i -> function.apply(values[i], vector.values[i])).toArray()));
    }

    public DoubleVector add(DoubleVector vector) {
        return elementOperation(vector, (x, y) -> x + y);
    }

    public DoubleVector subtract(DoubleVector vector) {
        return elementOperation(vector, (x, y) -> x - y);
    }

    public DoubleVector multiplyBy(double k) {
        return elementOperation(this, (x, v) -> k * x);
    }

    public double scalar(DoubleVector vector) {
        return Arrays.stream(elementOperation(vector, (x, y) -> x * y).values).reduce(0d, (x, y) -> x + y).doubleValue();
    }

    public double norm() {
        return Math.sqrt(scalar(this));
    }

    public Double get(int i) {
        if (i < 0 || i >= n) {
            throw new IndexOutOfBoundsException("Index should be in range [0, " + n + ").");
        }
        return values[i];
    }

    public void set(int i, Double v) {
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

    public DoubleVector multiply(Matrix matrix) {
        if (n != matrix.getN()) {
            throw new IndexOutOfBoundsException("cringe");
        }
        return  matrix.transpose().multiply(this);
    }

    public Stream<Double> stream() {
        return Arrays.stream(values);
    }
}
