package slau.matrix;

public class GilbertMatrix implements Matrix {
    private int n;

    public GilbertMatrix(int n) {
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
    public double get(int row, int col) {
        return 1d / (row + col + 1);
    }

    @Override
    public void set(int row, int col, double value) {
        throw new UnsupportedOperationException("no");
    }
}
