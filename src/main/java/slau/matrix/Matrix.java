package slau.matrix;

public interface Matrix {
    double get(int row, int col);
    int getN();
    int getM();
    void set(int row, int col, double value);
}
