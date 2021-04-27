package functions;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;
import java.util.function.IntPredicate;
import java.util.stream.IntStream;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

public class QuadraticForm {
    private final int n;
    private final Matrix a;
    private final DoubleVector b;
    private final double c;
    private final DoubleVector values;
    private final double minValue, maxValue;

    private static double eps = 1e-6d;

    // public QuadraticForm(double[][] a, double[] b, double c, double[] values) {
    //     this.n = a.length;
    //     this.a = a;
    //     if (Arrays.stream(a).anyMatch(Objects::isNull) || !(checkMatrix())) {
    //         throw new IllegalArgumentException("Illegal matrix input.");
    //     }
    //     if (b.length != n) {
    //         throw new IllegalArgumentException("Vector and Matrix should have same dimension.");
    //     }
    //     this.b = b;
    //     this.c = c;
    //     // if (values.length != n) {
    //     //     throw new IllegalArgumentException("values and Matrix should have same dimension.");
    //     // }
    //     this.values = values;
    //     this.minValue = Arrays.stream(values).min().orElse(0);
    //     this.maxValue = Arrays.stream(values).max().orElse(0);
    // }

    public QuadraticForm(Matrix a, DoubleVector b, double c, DoubleVector values) {
        this.n = a.getN();
        this.a = a;
        if (a.stream().anyMatch(Objects::isNull) || !(checkMatrix())) {
            throw new IllegalArgumentException("Illegal matrix input.");
        }
        if (b.size() != n) {
            throw new IllegalArgumentException("Vector and Matrix should have same dimension.");
        }
        this.b = b;
        this.c = c;
        // if (values.length != n) {
        //     throw new IllegalArgumentException("values and Matrix should have same dimension.");
        // }
        this.values = values;
        this.minValue = values.stream().min(Comparator.naturalOrder()).orElse(0d);
        this.maxValue = values.stream().max(Comparator.naturalOrder()).orElse(0d);
    }

    public QuadraticForm(Matrix a, DoubleVector b, Double c) {
        this(a, b, c, new DoubleVector(1d));
    }

    public int getN() {
        return n;
    }

    public double getMinValue() {
        return minValue;
    }

    public double getMaxValue() {
        return maxValue;
    }

    private IntStream range() {
        return IntStream.range(0, n);
    }

    private boolean checkMatrix() {
        IntPredicate checkRow = i -> range().allMatch(j -> compare(a.get(i, j), a.get(j, i)));
        return a.stream().allMatch(row -> row.size() == n) && range().allMatch(checkRow);
    }

    private double scalarProduct(DoubleVector x, DoubleVector y) {
        return range().mapToDouble(i -> x.get(i) * y.get(i)).sum();
    }

    public double apply(DoubleVector x) {
        return scalarProduct(new DoubleVector(range().mapToDouble(i -> scalarProduct(x, a.get(i))).toArray()), x) / 2d
            + scalarProduct(x, b) + c;
    }

    public DoubleVector gradient(DoubleVector x) {
        return new DoubleVector(range().mapToDouble(i -> (scalarProduct(x, a.get(i)) + b.get(i))).toArray());
    }

    public Matrix getA() {
        return a;
    }

    public DoubleVector getB() {
        return b;
    }

    @Override
    public String toString() {
        return a.toString();
    }

    private static boolean compare(double a, double b) {
        return Math.abs(a - b) <= eps;
    }
}
