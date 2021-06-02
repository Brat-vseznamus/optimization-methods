package matrix;

public class LUMatrix extends RegularMatrix implements LUDecomposible {

    public LUMatrix(final RegularMatrix m) {
        this(m.data);
    }

    public LUMatrix(final int n, final int m) {
        super(n, m);
    }

    public LUMatrix(final double[][] data) {
        super(data);
        if (!checkMinors(data)) {
            throw new IllegalArgumentException("all minors can't be 0s");
        }
    }

    /* TODO: экономия в том, что мы матрицу считает только u и l-1 и умещаем их вплотную
        так как в лу мы решаем  lux = y <=> ux = l-1y
        мб сделать по 12ой лекции
    */
    @Override
    public LU getLU() {
        final double[][] u = new double[n][n];
        for (int i = 0; i < n; i++) {
            System.arraycopy(data[i], 0, u[i], 0, n);
        }
        final double[][] l = reverse(reverse(u));
        return new LU(
                new RegularMatrix(l),
                new RegularMatrix(u));
    }

    public static double[][] reverse(final double[][] l0) {
        final int n = l0.length;
        final double[][] l = new double[n][n];
        for (int i = 0; i < n; i++) {
            l[i][i] = 1;
        }
        for (int row = 0; row < n; row++) {
            final double aii = l0[row][row];
            for (int row2 = row + 1; row2 < n; row2++) {
                final double aji = l0[row2][row];
                for (int col = row; col < n; col++) {
                    l0[row2][col] -= aji/aii * l0[row][col];
                }
                for (int col = 0; col < n; col++) {
                    l[row2][col] -= aji/aii * l[row][col];
                }
            }
        }
        return l;
    }


    // TODO: do it
    private boolean checkMinors(final double[][] matrix) {
        return true;
    }
}
