package slau.utils;

import methods.Pair;
import matrix.Matrix;
import slau.methods.Method;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.stream.IntStream;

public class TableGenerator {
    public static class Setting {
        int base;
        int maxExp;
        int maxK;

        public Setting(final int base, final int maxExp, final int maxK) {
            this.base = base;
            this.maxExp = maxExp;
            this.maxK = maxK;
        }
    }

    public static void generateTable(final Method method,
                                     final Setting setting) {
        generateTable(String.format("%s[%d, %d, %d]",
                        method.getClass().getSimpleName(),
                        setting.base,
                        setting.maxExp,
                        setting.maxK
               ),method, setting);
    }

    public static void generateTable(final String filename,
                                     final Method method,
                                     final Setting setting) {
        generateTable(filename, method, setting, FormulaGenerator::generateMatrix2);
    }

    public static void generateTable(final String filename,
                                     final Method method,
                                     final Setting setting,
                                     final BiFunction<Integer, Integer, Matrix> generator) {
        final String format = "%4s  %2s  %8.6e  %8.6e%n";
        Path path = null;
        try {
            path = Path.of("src/main/java/slau"+
                    File.separator
                    + "resources"
                    + File.separator
                    + filename
                    + ".txt");
            if (!Files.exists(path)) {
                path = Files.createFile(path);
            }
        } catch (final IOException e) {
            e.printStackTrace();
        }
        int n = 1;
        final int maxK = setting.maxK;
        final int inc = setting.base;
        final int maxExp = setting.maxExp;
        final int avg = 5;

        try (final BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (int exp = 1; exp <= maxExp; exp++) {
                n *= inc;
                final double[] answer = new double[n];
                IntStream.range(0, n).forEach(i -> answer[i] = i + 1);

                for (int k = 0; k <= maxK; k++) {
                    double r1 = 0;
                    double r2 = 0;
                    int success = 0;
                    for (int attempt = 0; attempt < avg; attempt++) {
                        final Pair<Matrix, double[]> formula = FormulaGenerator.generateFormula(n, k, generator);
                        double[] result;
                        int cnt = 0;
                        do {
                            result = method.solve(
                                    formula.first,
                                    formula.second
                            );
                        } while (cnt++ < 10 && Arrays.stream(result).anyMatch(Double::isNaN));
                        if (Arrays.stream(result).noneMatch(Double::isNaN)) {
                            r1 += VectorUtils.euclideanNorm(
                                    VectorUtils.subtract(result, answer)
                            );
                            r2 += VectorUtils.euclideanNorm(
                                    VectorUtils.subtract(result, answer)
                            ) /
                                    VectorUtils.euclideanNorm(answer);
                            success++;
                        }
                    }
                    if (success != 0) {
                        writer.write(String.format(format,
                                n,
                                k,
                                r1 / success,
                                r2 / success));
                    } else {
                        writer.write("attempt wasn't successful\n");
                    }
                    System.out.printf("row for [%d, %d] done%n", n, k);
                }
            }
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    public static void generateTable(final String filename, final Method method) {
        generateTable(filename, method, new Setting(3, 3, 5));
    }

    public static void generateTableHilbert(final String filename, final Method method, final int[] dims) {
        final String format = "%4s  %8.6e  %8.6e%n";
        Path path = null;
        try {
            path = Path.of("src/main/java/slau"+
                    File.separator
                    + "resources"
                    + File.separator
                    + filename
                    + ".txt");
            if (!Files.exists(path)) {
                path = Files.createFile(path);
            }
        } catch (final IOException e) {
            e.printStackTrace();
        }

        try (final BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (int n : dims) {
                final double[] answer = new double[n];
                IntStream.range(0, n).forEach(i -> answer[i] = i + 1);
                final Pair<Matrix, double[]> formula = FormulaGenerator.generateHilbertFormula(n);
                int cnt = 0;
                double[] result;
                do {
                    result = method.solve(
                            formula.first,
                            formula.second
                    );

                } while (cnt++ < 5 && Arrays.stream(result).anyMatch(Double::isNaN));
                writer.write(String.format(format,
                    n,
                    VectorUtils.euclideanNorm(
                            VectorUtils.subtract(result, answer)
                    ),
                    VectorUtils.euclideanNorm(
                            VectorUtils.subtract(result, answer)
                    ) /
                            VectorUtils.euclideanNorm(answer)));
            }
        } catch (final IOException e) {
            e.printStackTrace();
        }

    }

}
