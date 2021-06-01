package slau.utils;

import methods.Pair;
import slau.matrix.LUMatrix;
import slau.matrix.Matrix;
import slau.matrix.ProfileMatrix;
import slau.matrix.RegularMatrix;

import java.util.Arrays;
import java.util.Random;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.IntStream;

public class FormulaGenerator {

    public static Pair<Matrix, double[]>
        generateFormula(
            final int n,
            final int k,
            final BiFunction<Integer, Integer, Matrix> generator) {
        final Matrix m = generator.apply(n, k);
        final double[] x = new double[n];
        IntStream.range(0, n).forEach(i -> x[i] = i + 1);
        return new Pair<>(m, m.multiplyBy(x));
    }

    public static Pair<Matrix, double[]> generateFormula(final int n, final int k) {
        return generateFormula(n, k, FormulaGenerator::generateMatrix2);
    }

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
                    double sum = IntStream.range(0, n - 1)
                            .mapToDouble(i -> data[row][i])
                            .sum();
                    double last = -rnd.nextInt(5);
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

    private static Supplier<Integer> random5 = () -> {
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
                    double sum = IntStream.range(0, n - 1)
                            .mapToDouble(i -> data[row][i])
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
                    double sum = IntStream.range(0, n - 1)
                            .mapToDouble(i -> data[row][i])
                            .sum();
                    double last = -random5.get();
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

    public static Matrix generateHilbert(int n) {
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

    public static Pair<Matrix, double[]> generateHilbertFormula(final int n) {
        final Matrix m = generateHilbert(n);
        final double[] x = new double[n];
        IntStream.range(0, n).forEach(i -> x[i] = i + 1);
        return new Pair<>(m, m.multiplyBy(x));
    }

    public static Matrix generateDiagonal(int n, int k) {
        final double[][] data = new double[n][n];
        int range = n / 10;
        int[] diagonals = new int[range];
        IntStream.range(0, range).forEach(
                i -> diagonals[i] = new Random().nextInt(2*(n - 1) + 1) - (n - 1));
        for (int j = -n + 1; j < n; j++) {
            for (int i = 0; i < n; i++) {
                if (0 <= i + j && i + j < n) {
                    data[i + j][i] = -RandomUtils.randomValue(0.5);
                }
            }
        }
        for (int dig : diagonals) {
            for (int i = 0; i < n; i++) {
                if (0 <= i + dig  && i + dig < n) {
                    data[i + dig][i] = -RandomUtils.randomValue(0.05);
                }
            }
        }

        double eps = 1;
        while (k-- > 0) eps /= 10d;
        for (int i = 0; i < n; i++) {
            int finalI = i;
            double sum = IntStream.range(0, n)
                    .mapToDouble(j -> data[finalI][j]).sum();
            if (sum == 0) {
                int changer = (i + (int)RandomUtils.randomValue(1, n, 0d)) % n;
                double val = data[i][changer];
                double newVal = -(1 - (int)val) % 5;
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
        static double randomValue(int l, int r, double zeroPercent) {
            Random rnd = new Random();
            if (rnd.nextDouble() <= zeroPercent) {
                return 0d;
            }
            return rnd.nextInt(r - l) + l;
        }

        static double randomValue(double zeroPercent) {
            return randomValue(1, 5, zeroPercent);
        }

    }
}
