package linear;

import slau.utils.PresentationUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

import static linear.Matrices.epsEquals;

public class ProfileMatrix extends AbstractMatrix implements LUDecomposible, PresentableMatrix {
    private final int n;
    private final double[] diagonal;
    private final double[] rowProfile;
    private final double[] colProfile;
    private final int[] rowBeginInProfile;
    private final int[] colBeginInProfile;

    public ProfileMatrix(final double[][] data) {
        Matrices.requireSquare(data);

        n = data.length;
        diagonal = new double[n];
        IntStream.range(0, n).forEach(i -> diagonal[i] = data[i][i]);

        rowBeginInProfile = new int[n + 1];
        colBeginInProfile = new int[n + 1];

        final List<Double> rowElementsList = new ArrayList<>();
        for (int row = 0; row < n; row++) {
            rowBeginInProfile[row] = rowElementsList.size();
            boolean wasNonZero = false;
            for (int col = 0; col < row; col++) {
                if (epsEquals(data[row][col], 0d)) {
                    wasNonZero = true;
                }

                if (wasNonZero) {
                    rowElementsList.add(data[row][col]);
                }
            }
        }
        rowBeginInProfile[n] = rowElementsList.size();
        rowProfile = rowElementsList.stream().mapToDouble((el) -> el).toArray();

        final List<Double> colElementsList = new ArrayList<>();
        for (int col = 0; col < n; col++) {
            colBeginInProfile[col] = colElementsList.size();
            boolean wasNonZero = false;
            for (int row = 0; row < col; row++) {
                if (epsEquals(data[row][col], 0d)) {
                    wasNonZero = true;
                }

                if (wasNonZero) {
                    colElementsList.add(data[row][col]);
                }
            }
        }
        colBeginInProfile[n] = colElementsList.size();
        colProfile = colElementsList.stream().mapToDouble((el) -> el).toArray();
    }

    /*
    n
    double[] diagonal;
    rowProfileSize
    double[] rowProfile;
    colProfileSize
    double[] colProfile;
    int[] rowBeginInProfile;
    int[] colBeginInProfile;
     */
    public ProfileMatrix(final Path filePath) {
        try (final BufferedReader reader = Files.newBufferedReader(filePath)) {
            final Scanner scanner = new Scanner(reader);
            n = scanner.nextInt();
            diagonal = new double[n];
            PresentationUtils.readDoubleArrayFromScanner(diagonal, scanner);
            final int rowProfileSize = scanner.nextInt();
            rowProfile = new double[rowProfileSize];
            PresentationUtils.readDoubleArrayFromScanner(rowProfile, scanner);
            final int colProfileSize = scanner.nextInt();
            colProfile = new double[colProfileSize];
            PresentationUtils.readDoubleArrayFromScanner(colProfile, scanner);
            rowBeginInProfile = new int[n + 1];
            PresentationUtils.readIntArrayFromScanner(rowBeginInProfile, scanner);
            colBeginInProfile = new int[n + 1];
            PresentationUtils.readIntArrayFromScanner(colBeginInProfile, scanner);
        } catch (final IOException e) {
            System.err.println("Exception while reading: " + e.getMessage());
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public int getN() {
        return n;
    }

    @Override
    public int getM() {
        return n;
    }

    private int rowCount(final int row) {
        return rowBeginInProfile[row + 1] - rowBeginInProfile[row];
    }

    private int colCount(final int col) {
        return colBeginInProfile[col + 1] - colBeginInProfile[col];
    }

    private int rowToBeginCol(final int row) {
        return row - rowCount(row);
    }

    private int colToBeginRow(final int col) {
        return col - colCount(col);
    }

    private boolean outOfProfile(final int row, final int col) {
        return row != col
                && (row < col && colCount(col) < col - row
                || rowCount(row) < row - col);
    }

    private int getColProfileIndex(final int row, final int col) {
        return colBeginInProfile[col] + row - colToBeginRow(col);
    }

    private int getRowProfileIndex(final int row, final int col) {
        return rowBeginInProfile[row] + col - rowToBeginCol(row);
    }

    @Override
    public double get(final int row, final int col) {
        if (outOfProfile(row, col)) {
            return 0d;
        }

        if (row == col) {
            return diagonal[row];
        } else if (row < col) {
            return colProfile[getColProfileIndex(row, col)];
        } else {
            return rowProfile[getRowProfileIndex(row, col)];
        }
    }

    @Override
    public void set(final int row, final int col, final double value) {
        if (outOfProfile(row, col)) {
            throw new UnsupportedOperationException("row and col must be in profile of matrix");
        }

        if (row == col) {
            diagonal[row] = value;
        } else if (row < col) {
            colProfile[getColProfileIndex(row, col)] = value;
        } else {
            rowProfile[getRowProfileIndex(row, col)] = value;
        }
    }

    @Override
    public boolean isDiagonal() {
        return false;
    }

    @Override
    public LU getLU() {
        final LComponent L = new LComponent();
        final UComponent U = new UComponent();

        L.set(0, 0, this.get(0, 0));
        for (int i = 0; i < n; ++i) {
            for (int j = rowToBeginCol(i); j < i; ++j) {
                double sum = 0d;
                for (int k = rowToBeginCol(i); k < j; ++k) {
                    sum += L.get(i, k) * U.get(k, j);
                }
                L.set(i, j, this.get(i, j) - sum);
            }

            for (int j = colToBeginRow(i); j < i; ++j) {
                double sum = 0d;
                for (int k = colToBeginRow(i); k < j; ++k) {
                    sum += L.get(j, k) * U.get(k, i);
                }
                U.set(j, i, (this.get(j, i) - sum) / L.get(j, j));
            }

            double sum = 0d;
            for (int k = 0; k < i; ++k) {
                sum += L.get(i, k) * U.get(k, i);
            }
            L.set(i, i, this.get(i, i) - sum);
        }

        return new LU(L, U);
    }

    /*
    n
    double[] diagonal;
    rowProfileSize
    double[] rowProfile;
    colProfileSize
    double[] colProfile;
    int[] rowBeginInProfile;
    int[] colBeginInProfile;
     */
    @Override
    public void saveToFile(final Path file) {
        PresentationUtils.createDir(file);
        try (final BufferedWriter writer = Files.newBufferedWriter(file)) {
            writer.write(Integer.toString(n));
            writer.write(System.lineSeparator());

            PresentationUtils.writeDoubleArray(diagonal, writer);
            writer.write(System.lineSeparator());

            writer.write(Integer.toString(rowProfile.length));
            writer.write(System.lineSeparator());
            PresentationUtils.writeDoubleArray(rowProfile, writer);
            writer.write(System.lineSeparator());

            writer.write(Integer.toString(colProfile.length));
            writer.write(System.lineSeparator());
            PresentationUtils.writeDoubleArray(colProfile, writer);
            writer.write(System.lineSeparator());

            PresentationUtils.writeIntArray(rowBeginInProfile, writer);
            writer.write(System.lineSeparator());

            PresentationUtils.writeIntArray(colBeginInProfile, writer);
            writer.write(System.lineSeparator());
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    private abstract class AbstractComponent extends AbstractMatrix {

        @Override
        public int getN() {
            return n;
        }

        @Override
        public int getM() {
            return n;
        }

        @Override
        public double get(final int row, final int col) {
            return ProfileMatrix.this.get(row, col);
        }

        @Override
        public void set(final int row, final int col, final double value) {
            ProfileMatrix.this.set(row, col, value);
        }
    }

    private class LComponent extends AbstractComponent implements Matrix {

        @Override
        public double get(final int row, final int col) {
            if (row < col) {
                return 0d;
            }

            return super.get(row, col);
        }

        @Override
        public void set(final int row, final int col, final double value) {
            if (row < col) {
//                throw new UnsupportedOperationException("cannot set in left down triangle");
                return;
            }
            super.set(row, col, value);
        }

        @Override
        public boolean isDiagonal() {
            return false;
        }
    }

    private class UComponent extends AbstractComponent implements Matrix {

        @Override
        public double get(final int row, final int col) {
            if (col < row) {
                return 0d;
            }
            if (row == col) {
                return 1d;
            }

            return super.get(row, col);
        }

        @Override
        public void set(final int row, final int col, final double value) {
            if (col < row || row == col) {
//                throw new UnsupportedOperationException("cannot set in right upper triangle or diagonal");
                return;
            }
            super.set(row, col, value);
        }

        @Override
        public boolean isDiagonal() {
            return false;
        }
    }
}
