package slau.matrix;

public class GilbertMatrix implements Matrix {
    private final int n;

    public GilbertMatrix(final int n) {
        this.n = n;
    }

    @Override
    public int getN() {
        return n;
    }

    @Override
    public int getM() {
        return getN();
    }

    @Override
    public double get(final int row, final int col) {
        return 1d / (row + col + 1);
    }

    @Override
    public void set(final int row, final int col, final double value) {
        throw new UnsupportedOperationException("no");
    }
}
