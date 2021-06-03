import linear.DoubleVector;
import linear.Matrices;
import org.junit.jupiter.api.Assertions;

import java.util.stream.IntStream;

public abstract class TestUtils {

    final static double DOUBLE_EQUALS_EPS = 0.001;

    static void assertEqualsDoubleArrays(final double[] first,
                                         final double[] second) {
        Assertions.assertEquals(first.length, second.length);
        IntStream.range(0, first.length).forEach(index -> Assertions.assertEquals(first[index], second[index], DOUBLE_EQUALS_EPS));
    }

    static void assertEqualsDoubleVectorNorms(final double[] first, final double[] second) {
        Assertions.assertTrue(Matrices.epsEquals(new DoubleVector(first).subtract(new DoubleVector(second)).norm(), 0d));
    }
}
