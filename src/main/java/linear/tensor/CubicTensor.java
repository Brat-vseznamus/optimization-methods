package linear.tensor;

import linear.DoubleMatrix;
import linear.DoubleVector;
import linear.Matrix;

import java.util.Arrays;

public class CubicTensor implements Tensor {
    private double[][][] values;

    public CubicTensor(double[][][] values) {
        this.values = values;
    }

    @Override
    public double getValue(int... indices) {
        if (indices.length != 3) {
            throw new IllegalArgumentException("wrong length");
        }
        return values[indices[0]][indices[1]][indices[2]];
    }

    @Override
    public DoubleVector getVector(int... indices) {
        if (indices.length != 2) {
            throw new IllegalArgumentException("wrong length");
        }
        int i = indices[0];
        int j = indices[1];
        return new DoubleVector(
                Arrays.stream(values[i][j], 0, values.length)
                    .toArray());
    }

    @Override
    public Matrix getMatrix(int... indices) {
        if (indices.length != 1) {
            throw new IllegalArgumentException("wrong length");
        }
        int i = indices[0];
        return new DoubleMatrix(
                getVector(i, 0),
                getVector(i, 1),
                getVector(i, 2));
    }

    @Override
    public Tensor getTensor(int... indices) {
        return this;
    }

    @Override
    public int getDimensional() {
        return 3;
    }
}
