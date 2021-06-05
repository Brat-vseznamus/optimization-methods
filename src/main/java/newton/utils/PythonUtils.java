package newton.utils;

import slau.utils.PresentationUtils;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public abstract class PythonUtils {
    public static void printTwoDimensionalIterationsToFile(final List<Iteration> list, String fileName) {
        fileName = "pythonUtils/iterations/" + fileName;
        PresentationUtils.createDir(Path.of(fileName));
        try (final BufferedWriter writer = Files.newBufferedWriter(Path.of(fileName))) {
            for (final Iteration iteration : list) {
                writer.write(PresentationUtils.getFormattedDouble(iteration.getX0().get(0)));
                writer.write(" ");

                writer.write(PresentationUtils.getFormattedDouble(iteration.getF0()));
                writer.write(" ");

                writer.write(PresentationUtils.getFormattedDouble(iteration.getX1().get(0)));
                writer.write(" ");

                writer.write(PresentationUtils.getFormattedDouble(iteration.getF1()));
                writer.write(" ");

                writer.write(PresentationUtils.getFormattedDouble(iteration.getSlope().get(0)));
                writer.write(System.lineSeparator());
            }
        } catch (final IOException e) {
            System.err.println("Error while saving iterations: " + e.getMessage());
        }
    }
}
