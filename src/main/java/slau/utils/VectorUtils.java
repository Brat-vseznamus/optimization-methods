package slau.utils;

import java.util.Arrays;
import java.util.stream.IntStream;

public class VectorUtils {

    public static double[] add(final double[] a, final double[] b) {
        if (a.length != b.length) {
            throw  new IllegalArgumentException("vectors don't have same sizes");
        }
        final int n = a.length;
        final double[] res = new double[n];
        IntStream
            .range(0, n)
            .forEach(i -> res[i] = a[i] + b[i]);
        return res;
    }

    public static double[] muliplyBy(final double[] a, final int k) {
        final int n = a.length;
        final double[] res = new double[n];
        IntStream
                .range(0, n)
                .forEach(i -> res[i] = a[i] * k);
        return res;
    }

    public static double[] subtract(final double[] a, final double[] b) {
        return add(a, muliplyBy(b, -1));
    }

    public static double euclideanNorm(final double[] a) {
        final int n = a.length;
        final double res = Arrays.stream(a).map(v -> v * v)
                .sum();
        return Math.sqrt(res);
    }
}
