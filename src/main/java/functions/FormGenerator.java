package functions;

import java.util.Random;
import java.util.stream.IntStream;
public class FormGenerator {
    private static final double RANGE = 10;
    public static QuadraticForm generate(final int n, final int k) {
        if (k < 1) {
            return null;
        }
        final Random random = new Random();
        if (n == 1) {
            return new QuadraticForm(new Matrix(new DoubleVector(k), true), new DoubleVector(new double[]{k * random.nextDouble()}), 0d);
        }
        final double[] a = new double[n];
        final double l = 1;
        final double ll = k * l;
        IntStream.range(0, n).forEach(
            i -> a[i] = l + (ll - l) * random.nextDouble()
        );
        final double[] b = IntStream.range(0, n).mapToDouble(i -> new Random().nextDouble() * RANGE - RANGE/2).toArray();
        final double c = new Random().nextDouble() * RANGE - RANGE/2;
        a[0] = l;
        a[1] = ll;
        return new QuadraticForm(new Matrix(new DoubleVector(a), true), new DoubleVector(b), c);
    }
}
