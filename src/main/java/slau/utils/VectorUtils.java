package slau.utils;

import java.util.stream.IntStream;

public class VectorUtils {

    public static double[] add(double[] a, double[] b) {
        if (a.length != b.length) {
            throw  new IllegalArgumentException("vectors don't have same sizes");
        }
        int n = a.length;
        double[] res = new double[n];
        IntStream
            .range(0, n)
            .forEach(i -> res[i] = a[i] + b[i]);
        return res;
    }

    public static double[] muliplyBy(double[] a, int k) {
        int n = a.length;
        double[] res = new double[n];
        IntStream
                .range(0, n)
                .forEach(i -> res[i] = a[i] * k);
        return res;
    }

    public static double[] subtract(double[] a, double[] b) {
        return add(a, muliplyBy(b, -1));
    }

    public static double euclideanNorm(double[] a) {
        int n = a.length;
        double res = IntStream
                .range(0, n)
                .mapToDouble(i -> a[i] * a[i] )
                .sum();
        return Math.sqrt(res);
    }
}
