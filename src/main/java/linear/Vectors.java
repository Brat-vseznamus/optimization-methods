package linear;

import java.util.Arrays;
import java.util.Objects;

public class Vectors {
    public static final double EPS = 1e-9d;

    private Vectors() {
    }

    public static void requireNonNull(final DoubleVector... vectors) {
        if (vectors == null || Arrays.stream(vectors).anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException("vectors must be non null");
        }
    }

    public static boolean checkEqualSizes(final DoubleVector... vectors) {
        return Arrays.stream(vectors).allMatch(vector -> vector.size() == vectors[0].size());
    }

    public static void requireEqualSizes(final DoubleVector... vectors) {
        if (!checkEqualSizes(vectors)) {
            throw new IllegalArgumentException("vectors must have same sizes");
        }
    }
}
