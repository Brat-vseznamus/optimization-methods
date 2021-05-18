package slau.matrix;

public class LUMatrix extends RegularMatrix implements LUDecomposible {

    public LUMatrix(RegularMatrix m) {
        this(m.data);
    }

    public LUMatrix(int n, int m) {
        super(n, m);
    }

    public LUMatrix(double[][] data) {
        super(data);
        if (!checkMinors(data)) {
            throw new IllegalArgumentException("all minors can't be 0s");
        }
    }

    @Override
    public LU getLU() {
        RegularMatrix E = new RegularMatrix(n, n);
        for (int i = 0; i < n; i++) {
            E.data[i][i] = 1;
        }
        for (int row = 0; row < n; row++) {
            double aii = data[row][row];
            for (int row2 = row + 1; row2 < n; row2++) {
                double aji = data[row2][row];
                for (int col = row; col < n; col++) {
                    data[row2][col] -= aji/aii * data[row][col];
                }
                for (int col = 0; col < n; col++) {
                    E.data[row2][col] -= aji/aii * E.data[row][col];
                }
            }
        }
        double[][] u = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                u[i][j] = data[i][j];
            }
        }
        return null;
    }

//    public double[][] reverse

    // TODO: do it
    private boolean checkMinors(double[][] matrix) {
        return true;
    }
}
