package functions;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.IntPredicate;
import java.util.stream.IntStream;
import java.util.stream.DoubleStream;

public class QuadraticForm {
    private final int n;
    private final double[][] a;
    private final double[] b;
    private final double c;

    public QuadraticForm(double[][] a, double[] b, double c) {
        this.n = a.length;
        this.a = a;
        if (Arrays.stream(a).anyMatch(Objects::isNull) || !(checkMatrix())) {
            throw new IllegalArgumentException("Illegal matrix input.");
        }
        if (b.length != n) {
            throw new IllegalArgumentException("Vector and Matrix should have same dimension.");
        }
        this.b = b;
        this.c = c;
    }

    private IntStream range() {
        return IntStream.range(0, n);
    }

    private boolean checkMatrix() {
        IntPredicate checkRow = i -> range().allMatch(j -> a[i][j] == a[j][i]);
        return Arrays.stream(a).allMatch(row -> row.length == n) && range().allMatch(checkRow);
    }

    private double scalarProduct(double[] x, double[] y) {
        return range().mapToDouble(i -> x[i] * y[i]).sum();
    }

    public double apply(double[] x) {
        return scalarProduct(range().mapToDouble(i -> scalarProduct(x, a[i])).toArray(), x) / 2d
            + scalarProduct(x, b) + c;
    }

    public double[] gradient(double[] x) {
        return range().mapToDouble(i -> (scalarProduct(x, a[i]) + b[i])).toArray();
    }

    @Override
    public String toString() {
        return Arrays.deepToString(a);
    }
}
