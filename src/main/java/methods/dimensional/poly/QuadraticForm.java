package methods.dimensional.poly;

import linear.DoubleVector;
import linear.Matrices;
import linear.Matrix;

import java.util.Objects;
import java.util.function.IntPredicate;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

public class QuadraticForm {
    private static final double EPS = 1e-6d;

    private final int n;
    private final Matrix a;
    private final DoubleVector b;
    private final double c;
    private final DoubleVector values;
    private final double minValue, maxValue;


    public QuadraticForm(final Matrix a, final DoubleVector b, final double c, final DoubleVector values) {
        if (a.stream().anyMatch(Objects::isNull) || !(Matrices.checkSymmetric(a))) {
            throw new IllegalArgumentException("Illegal matrix input.");
        }
        this.n = a.getN();
        if (b.size() != n) {
            throw new IllegalArgumentException("Vector and Matrix should have same dimension.");
        }
        this.a = a;
        this.b = b;
        this.c = c;
        this.values = values;
        minValue = values.stream().min().orElse(1d);
        maxValue = values.stream().max().orElse(1d);
    }

    public QuadraticForm(final Matrix a, final DoubleVector b, final Double c) {
        this(a, b, c, new DoubleVector(IntStream.range(0, a.getN()).mapToDouble(i -> a.get(i, i)).toArray()));
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
/*

    private boolean checkMatrix() {
        return Matrices.checkSymmetric(a);
        if (a.isDiagonal()) {
            return true;
        }
        final IntPredicate checkRow = i -> range().allMatch(j -> compare(a.get(i, j), a.get(j, i)));
        return a.stream().allMatch(row -> row.size() == n) && range().allMatch(checkRow);
    }
*/

    private double scalarProduct(final DoubleVector x, final DoubleVector y) {
        return range().mapToDouble(i -> x.get(i) * y.get(i)).sum();
    }

    public double apply(final DoubleVector x) {
        if (a.isDiagonal()) {
            return scalarProduct(a.multiplyBy(x), x) / 2d + scalarProduct(x, b) + c;
        }
        return scalarProduct(new DoubleVector(range().mapToDouble(i -> scalarProduct(x, a.get(i))).toArray()), x) / 2d
                + scalarProduct(x, b) + c;
    }

    public DoubleVector gradient(final DoubleVector x) {
        if (a.isDiagonal()) {
            return a.multiplyBy(x).add(b);
        }
        return new DoubleVector(range().mapToDouble(i -> (scalarProduct(x, a.get(i)) + b.get(i))).toArray());
    }

    public Matrix getA() {
        return a;
    }

    public DoubleVector getB() {
        return b;
    }

    public double getC() {
        return c;
    }

    @Override
    public String toString() {
        return a.toString();
    }

    private static boolean compare(final double a, final double b) {
        return Math.abs(a - b) <= EPS;
    }
}
