package functions;

import java.util.Collections;
import java.util.Random;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;
public class FormGenerator {
    private static double range = 10;
    public static QuadraticForm generate(int n, int k) {
        if (k < 1) {
            return null;
        }
        Random random = new Random();
        if (n == 1) {
            return new QuadraticForm(new double[][]{{k}}, new double[]{k * random.nextDouble()}, 0);
        }
        double[][] a = new double[n][n];
        double l = random.nextDouble() * range/2d + range/2d;
        double ll = k * l;
        IntStream.range(0, n).forEach(
            i -> {
                a[i][i] = l + (ll - l) * random.nextDouble();
            }
        );
        double[] b = IntStream.range(0, n).mapToDouble(i -> new Random().nextDouble() * range - range/2).toArray();
        double c = new Random().nextDouble() * range - range/2;
        a[0][0] = l;
        a[1][1] = ll;
        return new QuadraticForm(a, b, c);
    }
}
