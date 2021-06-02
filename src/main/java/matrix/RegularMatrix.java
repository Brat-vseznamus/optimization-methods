package matrix;

import slau.utils.PresentationUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.IntStream;

public class RegularMatrix implements PresentableMatrix {
    protected double[][] data;
    protected final int n, m;

    public RegularMatrix(final int n, final int m) {
        this.n = n;
        this.m = m;
        data = new double[n][m];
    }

    public RegularMatrix(final double[][] data) {
        this(data.length, data[0].length);
        this.data = data;
        final int n = data.length;
        final int m = data[0].length;
        if (!IntStream.range(1, n).allMatch(i -> data[i].length == m)) {
            throw new IllegalArgumentException("all rows should have same size");
        }
    }

    public RegularMatrix(final Path pathFile) throws UncheckedIOException {
        try (final BufferedReader reader = Files.newBufferedReader(pathFile)) {
            final Scanner scanner = new Scanner(reader);
            n = scanner.nextInt();
            m = scanner.nextInt();
            data = new double[n][m];
            IntStream.range(0, n).forEach(row -> {
                IntStream.range(0, m).forEach(col -> {
                    data[row][col] = scanner.nextDouble();
                });
            });
        } catch (final IOException e) {
            System.err.println("Exception while reading: " + e.getMessage());
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public double get(final int row, final int col) {
        if (!validIndexes(row, col)) {
            throw new IllegalArgumentException(
                    "illegal row or column:[" + row + ", " + col + "]");
        }
        return data[row][col];
    }

    @Override
    public int getN() {
        return n;
    }

    @Override
    public int getM() {
        return m;
    }

    @Override
    public void set(final int row, final int col, final double value) {
        if (!validIndexes(row, col)) {
            throw new IllegalArgumentException(
                    "illegal row or column:[" + row + ", " + col + "]");
        }
        data[row][col] = value;
    }

    @Override
    public boolean isDiagonal() {
        return false;
    }

    protected boolean validIndexes(final int row, final int col) {
        return 0 <= row && row < n
                && 0 <= col && col < m;
    }

    @Override
    public String toString() {
        return Arrays.deepToString(data)
                .replace("],", "],\n");
    }

    @Override
    public void saveToFile(final Path file) {
        PresentationUtils.createDir(file);
        try (final BufferedWriter writer = Files.newBufferedWriter(file)) {
            writer.write(Integer.toString(n));
            writer.write(System.lineSeparator());
            writer.write(Integer.toString(m));
            writer.write(System.lineSeparator());
            IntStream.range(0, n).forEach(row -> {
                IntStream.range(0, m).forEach(col -> {
                    try {
                        writer.write(PresentationUtils.getFormattedDouble(data[row][col]));
                        writer.write(" ");
                    } catch (final IOException e) {
                        System.err.println("Exception while writing: " + e.getMessage());
                    }
                });
                try {
                    writer.write(System.lineSeparator());
                } catch (final IOException e) {
                    System.err.println("Exception while writing: " + e.getMessage());
                }
            });
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    public double[][] getData() {
        return data;
    }
}
