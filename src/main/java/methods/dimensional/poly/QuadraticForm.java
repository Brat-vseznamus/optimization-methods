package methods.dimensional.poly;

import java.util.Comparator;
import java.util.Objects;
import java.util.function.IntPredicate;
import java.util.stream.IntStream;

public class QuadraticForm {
    private final int n;
    private final Matrix a;
    private final DoubleVector b;
    private final double c;
    private final DoubleVector values;
    private final double minValue, maxValue;

    private static final double eps = 1e-6d;

    public QuadraticForm(final Matrix a, final DoubleVector b, final double c, final DoubleVector values) {
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
        this.values = values;
        if (a.isDiagonal()) {
            DoubleVector vals = a.getValues().get(0);
            this.minValue = vals.stream().min(Comparator.naturalOrder()).orElse(0d);
            this.maxValue = vals.stream().max(Comparator.naturalOrder()).orElse(0d);
        } else {
            this.minValue = values.stream().min(Comparator.naturalOrder()).orElse(0d);
            this.maxValue = values.stream().max(Comparator.naturalOrder()).orElse(0d);
        }

    }

    public QuadraticForm(final Matrix a, final DoubleVector b, final Double c) {
        this(a, b, c, new DoubleVector(1d));
    }

    public int getN() {
        return n;
    }

    public DoubleVector getValues() {
        return values;
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
        if (a.isDiagonal()) {
            return true;
        }
        final IntPredicate checkRow = i -> range().allMatch(j -> compare(a.get(i, j), a.get(j, i)));
        return a.stream().allMatch(row -> row.size() == n) && range().allMatch(checkRow);
    }

    private double scalarProduct(final DoubleVector x, final DoubleVector y) {
        return range().mapToDouble(i -> x.get(i) * y.get(i)).sum();
    }

    public double apply(final DoubleVector x) {
        if (a.isDiagonal()) {
            // System.out.println(a + " : " + ((a.multiply(x)).scalar(x) / 2d + scalarProduct(x, b) + c));
            return scalarProduct(a.multiply(x), x) / 2d + scalarProduct(x, b) + c;
        }
        return scalarProduct(new DoubleVector(range().mapToDouble(i -> scalarProduct(x, a.get(i))).toArray()), x) / 2d
            + scalarProduct(x, b) + c;
    }

    public DoubleVector gradient(final DoubleVector x) {
        if (a.isDiagonal()) {
            return a.multiply(x).add(b);
        }
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

    private static boolean compare(final double a, final double b) {
        return Math.abs(a - b) <= eps;
    }
}
