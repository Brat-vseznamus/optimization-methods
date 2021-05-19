package slau.utils;

import methods.Pair;
import slau.matrix.Matrix;
import slau.matrix.RegularMatrix;

import java.util.Random;
import java.util.stream.IntStream;

public class FormulaGenerator {
    public static Pair<Matrix, double[]> generateFormula(int n, int k) {
        Matrix m = generateMatrix(n, k);
        double[] x = new double[n];
        IntStream.range(0, n).forEach(i -> x[i] = i + 1);
        return new Pair<>(m, m.multiplyBy(x));
    }

    public static Matrix generateMatrix(int n, int k) {
        if (n < 0) {
            throw  new IllegalArgumentException("n >= 0");
        }
        final double eps = Math.exp(Math.log(10) * (-k));
        double[][] data = new double[n][n];
        Random rnd = new Random();
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
                    while (last + sum == 0) {
                        last = -rnd.nextInt(5);
                    }
                    sum += last;
                    data[row][n - 1] = last;
                    data[row][row] = -sum;
                    if (row == 0) {
                        data[row][row] += eps;
                    }
                }
        );
        return new RegularMatrix(data);
    }

}
