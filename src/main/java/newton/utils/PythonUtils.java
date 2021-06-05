package newton.utils;

import slau.utils.PresentationUtils;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public abstract class PythonUtils {
    public static void writeTwoDimIterations(final List<Iteration> list,
                                             String fileName,
                                             final String prefix,
                                             final List<Double> alphas) {
        fileName = "pythonUtils/iterations/" + fileName;
        PresentationUtils.createDir(Path.of(fileName));
        try (final BufferedWriter writer = Files.newBufferedWriter(Path.of(fileName))) {

            writer.write(prefix);
            writer.write(System.lineSeparator());

            for (final Double alpha : alphas) {
                writer.write(PresentationUtils.getFormattedDouble(alpha));
                writer.write(" ");
            }

            writer.write(System.lineSeparator());

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

    public static void printTwoDimensionalIterationsToFile(final List<Iteration> list, final String fileName) {
        writeTwoDimIterations(list, fileName,"", List.of());
    }
}
