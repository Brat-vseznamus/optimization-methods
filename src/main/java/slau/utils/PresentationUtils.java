package slau.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

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
}
