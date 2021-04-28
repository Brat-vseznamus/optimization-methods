package methods.dimensional.poly;

import java.util.Random;
import java.util.stream.IntStream;
public class FormGenerator {
    private static final double range = 10;
    public static QuadraticForm generate(final int n, final int k) {
        if (k < 1) {
            return null;
        }
        final Random random = new Random();
        if (n == 1) {
            return new QuadraticForm(new Matrix(new double[][]{{k}}), new DoubleVector(new double[]{k * random.nextDouble()}), 0d);
        }
        final double[][] a = new double[n][n];
        final double l = 1;
        final double ll = k * l;
        IntStream.range(0, n).forEach(
            i -> a[i][i] = l + (ll - l) * random.nextDouble()
        );
        final double[] b = IntStream.range(0, n).mapToDouble(i -> new Random().nextDouble() * range - range/2).toArray();
        final double c = new Random().nextDouble() * range - range/2;
        a[0][0] = l;
        a[1][1] = ll;
        return new QuadraticForm(new Matrix(a), new DoubleVector(b), c);
    }
}
