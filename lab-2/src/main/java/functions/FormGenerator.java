package functions;

import java.util.Collections;
import java.util.Random;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;
public class FormGenerator {
    private static final double range = 10;
    public static QuadraticForm generate(final int n, final int k) {
        if (k < 1) {
            return null;
        }
        final Random random = new Random();
        if (n == 1) {
            return new QuadraticForm(new Matrix(1, 1), new DoubleVector(k * random.nextDouble()), 0d);
        }
        final Matrix a = new Matrix(n, n);
        final double l = random.nextDouble() * range/2d + range/2d;
        final double ll = k * l;
        IntStream.range(0, n).forEach(
            i -> a.set(i, i, l + (ll - l) * random.nextDouble())
        );
        final DoubleVector b = new DoubleVector(IntStream.range(0, n)
                .mapToDouble(i -> new Random().nextDouble() * range - range/2).toArray());
        final double c = new Random().nextDouble() * range - range/2;
        a.set(0, 0, l);
        a.set(1, 1, ll);
        return new QuadraticForm(a, b, c);
    }
}
