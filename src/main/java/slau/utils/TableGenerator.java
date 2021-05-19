package slau.utils;

import methods.Pair;
import slau.matrix.Matrix;
import slau.methods.Method;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.IntStream;

public class TableGenerator {
    public static class Setting {
        int base;
        int maxExp;
        int maxK;

        public Setting(int base, int maxExp, int maxK) {
            this.base = base;
            this.maxExp = maxExp;
            this.maxK = maxK;
        }
    }

    public static void generateTable(String filename, Method method, Setting setting) {
        String format = "%4s  %2s  %8.6e  %8.6e%n";
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
        } catch (IOException e) {
            e.printStackTrace();
        }
        int n = 1;
        final int maxK = setting.maxK;
        final int inc = setting.base;
        final int maxExp = setting.maxExp;

        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (int exp = 1; exp <= maxExp; exp++) {
                n *= inc;
                double[] answer = new double[n];
                IntStream.range(0, n).forEach(i -> answer[i] = i + 1);

                for (int k = 0; k <= maxK; k++) {
                    Pair<Matrix, double[]> formula = FormulaGenerator.generateFormula(n, k);
                    double[] result = method.solve(
                            formula.first,
                            formula.second
                    );
                    writer.write(String.format(format,
                            n,
                            k,
                            VectorUtils.euclideanNorm(
                                    VectorUtils.subtract(result, answer)
                            ),
                            VectorUtils.euclideanNorm(
                                    VectorUtils.subtract(result, answer)
                            ) /
                            VectorUtils.euclideanNorm(answer)));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void generateTable(String filename, Method method) {
        generateTable(filename, method, new Setting(3, 3, 5));
    }

}
