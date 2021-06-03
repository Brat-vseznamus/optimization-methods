package matrix;

public interface Matrix {
    double DOUBLE_EQUALS_EPS = 0.0001;

    int getN();

    int getM();

    DoubleVector get(final int row);

    double get(int row, int col);

    void set(int row, int col, double value);

    boolean isDiagonal();

    void swapRows(final int row1, final int row2);

    boolean equals(final Matrix matrix);

    Matrix multiply(final Matrix matrix);

    double[] multiplyBy(final double[] vector);

    DoubleVector multiplyBy(final DoubleVector vector);

    double minor(int row, int col, int delta);
}
