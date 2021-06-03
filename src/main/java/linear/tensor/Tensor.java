package linear.tensor;

import linear.DoubleVector;
import linear.Matrix;

public interface Tensor {
    double getValue(int...indices);
    DoubleVector getVector(int...indices);
    Matrix getMatrix(int...indices);
    Tensor getTensor(int...indices);
    int getDimensional();
}
