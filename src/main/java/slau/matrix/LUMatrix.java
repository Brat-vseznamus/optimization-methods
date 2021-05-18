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
        return null;
    }

    // TODO: do it
    private boolean checkMinors(double[][] matrix) {
        return true;
    }
}
