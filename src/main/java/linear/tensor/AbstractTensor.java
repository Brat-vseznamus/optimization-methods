package linear.tensor;

import linear.DoubleVector;
import linear.Matrix;

import java.util.List;

// TODO
public abstract class AbstractTensor implements Tensor {
    protected int n;
    protected List<Integer> dims;
    protected List<Tensor> values;

    @Override
    public double getValue(int... indices) {
        return 0;
    }

    @Override
    public DoubleVector getVector(int... indices) {
        return null;
    }

    @Override
    public Matrix getMatrix(int... indices) {
        return null;
    }

    @Override
    public Tensor getTensor(int... indices) {
        return null;
    }
}
