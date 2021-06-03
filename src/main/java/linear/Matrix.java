package linear;

public interface Matrix {
    double DOUBLE_EQUALS_EPS = 0.0001;

    int getN();

    int getM();

    double get(int row, int col);

    DoubleVector get(final int row);

    void set(int row, int col, double value);

    boolean isDiagonal();

    void swapRows(final int row1, final int row2);

    boolean equals(final Matrix matrix);

    Matrix add(final Matrix matrix);

    Matrix subtract(final Matrix matrix);

    Matrix multiply(final Matrix matrix);

    double[] multiplyBy(final double[] vector);

    DoubleVector multiplyBy(final DoubleVector vector);

    double minor(int row, int col, int delta);

    double determinant();
}
