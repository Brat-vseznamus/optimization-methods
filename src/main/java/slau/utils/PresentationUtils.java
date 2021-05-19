package slau.utils;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.stream.IntStream;

public class PresentationUtils {
    public static String getFormattedDouble(final double x) {
        return String.format("%.6f", x);
    }

    public static void createDir(final Path path) {
        if (path.getParent() != null) {
            try {
                Files.createDirectories(path.getParent());
            } catch (final IOException e) {
                System.err.println("Exception while creating file: " + e.getMessage());
            }
        }
    }

    public static void writeIntArray(final int[] array, final BufferedWriter writer) throws IOException {
        for (final int el : array) {
            writer.write(Integer.toString(el));
            writer.write(" ");
        }
    }

    public static void writeDoubleArray(final double[] array, final BufferedWriter writer) throws IOException {
        for (final double el : array) {
            writer.write(getFormattedDouble(el));
            writer.write(" ");
        }
    }

    public static void readDoubleArrayFromScanner(final double[] data, final Scanner scanner) {
        IntStream.range(0, data.length).forEach(i -> {
            data[i] = scanner.nextDouble();
        });
    }

    public static void readIntArrayFromScanner(final int[] data, final Scanner scanner) {
        IntStream.range(0, data.length).forEach(i -> {
            data[i] = scanner.nextInt();
        });
    }

}
