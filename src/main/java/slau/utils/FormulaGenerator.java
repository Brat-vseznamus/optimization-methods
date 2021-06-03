package slau.utils;

import matrix.DoubleVector;
import methods.Pair;
import matrix.LUMatrix;
import matrix.Matrix;
import matrix.ProfileMatrix;

import java.util.Arrays;
import java.util.Random;
import java.util.function.BiFunction;
import java.util.function.Supplier;
import java.util.stream.IntStream;

public class FormulaGenerator {

    public static Pair<Matrix, DoubleVector>
        generateFormula(
            final int n,
            final int k,
            final BiFunction<Integer, Integer, Matrix> generator) {
        final Matrix m = generator.apply(n, k);
        final DoubleVector x = new DoubleVector(n);
        IntStream.range(0, n).forEach(i -> x.set(i, i + 1d));
        return new Pair<>(m, m.multiplyBy(x));
    }

    public static Pair<Matrix, DoubleVector> generateFormula(final int n, final int k) {
        return generateFormula(n, k, FormulaGenerator::generateMatrix2);
    }

    @Deprecated
    public static Matrix generateMatrix(final int n, final int k) {
        if (n <= 0) {
            throw  new IllegalArgumentException("n > 0");
        }
        double eps = 1;
        for (int i = 0; i < k; i++) {
            eps /= 10d;
        }
        final double[][] data = new double[n][n];
        final Random rnd = new Random();
        final double finalEps = eps;
        IntStream.range(0, n).forEach(
                row -> {
                    IntStream.range(0, n - 1).forEach(
                            col -> {
                                data[row][col] = -rnd.nextInt(5);
                            }
                    );
                    data[row][row] = 0;
                    double sum = Arrays.stream(data[row], 0, n - 1)
                            .sum();
                    final double last = -rnd.nextInt(5);
//                    while (last + sum == 0) {
//                        last = -rnd.nextInt(5);
//                    }
                    sum += last;
                    data[row][n - 1] = last;
                    data[row][row] = -sum;
                    if (row == 0) {
                        data[row][row] += finalEps;
                    }
                }
        );
        return new LUMatrix(data);
    }

    private static final Supplier<Integer> random5 = () -> {
        int res = 2;
        int[] ratios = new int[]{5, 2, 1, 1, 1};
        int len = Arrays.stream(ratios).sum();
        int[] rnd = new int[len];
        int cnt = 0;
        int point = 0;
        for (int i = 0; i < ratios.length; i++) {
            cnt += ratios[i];
            while (point < cnt) {
                rnd[point] = i;
                point++;
            }
        }
        return rnd[new Random().nextInt(rnd.length)];
    };

    public static Matrix generateMatrix2(final int n, final int k) {
        return genRandomMatrix(n, k, 0.5);
    }

    public static Matrix genRandomMatrix(final int n, final int k, final double zp) {
        if (n <= 0) {
            throw  new IllegalArgumentException("n > 0");
        }
        double eps = 1;
        for (int i = 0; i < k; i++) {
            eps /= 10d;
        }
        final double[][] data = new double[n][n];
        final Random rnd = new Random();
        final double finalEps = eps;
        IntStream.range(0, n).forEach(
                row -> {
                    IntStream.range(0, n - 1).forEach(
                            col -> {
                                data[row][col] = -RandomUtils.randomValue(zp);
                            }
                    );
                    data[row][row] = 0;
                    double sum = Arrays.stream(data[row], 0, n - 1)
                            .sum();
                    double last = -random5.get();
                    while (last + sum == 0) {
                        last = -rnd.nextInt(5);
                    }
                    sum += last;
                    data[row][n - 1] = last;
                    data[row][row] = -sum;
                    if (row == 0) {
                        data[row][row] += finalEps;
                    }
                }
        );
        return new LUMatrix(data);
    }

    public static Matrix generateMatrix3(final int n, final int k) {
        if (n <= 0) {
            throw  new IllegalArgumentException("n > 0");
        }
        double eps = 1;
        for (int i = 0; i < k; i++) {
            eps /= 10d;
        }


        final double[][] data = new double[n][n];
        final Random rnd = new Random();
        final double finalEps = eps;
        IntStream.range(0, n).forEach(
                row -> {
                    IntStream.range(0, n - 1).forEach(
                            col -> {
                                data[row][col] = -random5.get();
                            }
                    );
                    data[row][row] = 0;
                    double sum = Arrays.stream(data[row], 0, n - 1)
                            .sum();
                    final double last = -random5.get();
//                    while (last + sum == 0) {
//                        last = -rnd.nextInt(5);
//                    }
                    sum += last;
                    data[row][n - 1] = last;
                    data[row][row] = -sum;
                    if (row == 0) {
                        data[row][row] += finalEps;
                    }
                }
        );
        return new LUMatrix(data);
    }

    public static Matrix generateHilbert(final int n) {
        if (n <= 0) {
            throw  new IllegalArgumentException("n > 0");
        }
        final double[][] data = new double[n][n];
        IntStream.range(0, n).forEach(
                i -> IntStream.range(0, n).forEach(
                        j -> data[i][j] = 1d/(i + j + 1)
                )
        );
        return new ProfileMatrix(data);
    }

    public static Pair<Matrix, DoubleVector> generateHilbertFormula(final int n) {
        final Matrix m = generateHilbert(n);
        final DoubleVector x = new DoubleVector(n);
        IntStream.range(0, n).forEach(i -> x.set(i, i + 1d));
        return new Pair<>(m, m.multiplyBy(x));
    }

    public static Matrix generateDiagonal(final int n, int k) {
        final double[][] data = new double[n][n];
        final int range = n / 10;
        final int[] diagonals = new int[range];
        IntStream.range(0, range).forEach(
                i -> diagonals[i] = new Random().nextInt(2*(n - 1) + 1) - (n - 1));
        for (int j = -n + 1; j < n; j++) {
            for (int i = 0; i < n; i++) {
                if (0 <= i + j && i + j < n) {
                    data[i + j][i] = -RandomUtils.randomValue(0.5);
                }
            }
        }
        for (final int dig : diagonals) {
            for (int i = 0; i < n; i++) {
                if (0 <= i + dig  && i + dig < n) {
                    data[i + dig][i] = -RandomUtils.randomValue(0.05);
                }
            }
        }

        double eps = 1;
        while (k-- > 0) eps /= 10d;
        for (int i = 0; i < n; i++) {
            double sum = Arrays.stream(data[i], 0, n).sum();
            if (sum == 0) {
                final int changer = (i + (int)RandomUtils.randomValue(1, n, 0d)) % n;
                final double val = data[i][changer];
                final double newVal = -(1 - (int)val) % 5;
                data[i][changer] = newVal;
                sum += newVal - val;
            }
            data[i][i] = -sum + (i == 0 ? eps : 0);
        }

        return new LUMatrix(data);
    }

    private static class RandomUtils {
        /**
         * Generate random value from [l, r) or 0 with {@code zeroPercent} chance
         *
         * @param l left bound > 0
         * @param r right bound, r > l
         * @return randomValue with {@code zeroPercent} percent of 0, otherwise from l to r
         * */
        static double randomValue(final int l, final int r, final double zeroPercent) {
            final Random rnd = new Random();
            if (rnd.nextDouble() <= zeroPercent) {
                return 0d;
            }
            return rnd.nextInt(r - l) + l;
        }

        static double randomValue(final double zeroPercent) {
            return randomValue(1, 5, zeroPercent);
        }

    }
}
