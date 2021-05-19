package slau.matrix;

public class LU {
    Matrix l, u;

    public LU(final Matrix l, final Matrix u) {
        this.l = l;
        this.u = u;
    }

    public Matrix getL() {
        return l;
    }

    public Matrix getU() {
        return u;
    }

}
