package linear.tensor;

import java.util.Arrays;
import java.util.List;

public class TensorImpl<T> implements Tensor<T>{
    protected final List<? extends Tensor<T>> subTensors;
    protected final int dim;
    protected final int size;

    public TensorImpl(List<? extends Tensor<T>> tensors) {
        this.size = tensors.size();
        if (this.size == 0) {
            throw new IllegalArgumentException("wrong size");
        }
        int maxDim = tensors.stream().mapToInt(Tensor::getDimensional).max().orElse(0);
        if (!tensors.stream().allMatch(t -> t.getDimensional() == maxDim)) {
            throw new IllegalArgumentException("all tensors should be same sizes");
        }
        this.dim = maxDim + 1;
        this.subTensors = tensors;
    }

    @SafeVarargs
    public TensorImpl(Tensor<T>... tensors) {
        this(Arrays.asList(tensors));
    }

    @Override
    public T get(int... indices) {
        if (indices.length != dim) {
            throw new IllegalArgumentException("wrong size of input");
        }
        if (0 > indices[0] || indices[0] >= size) {
            throw new IllegalArgumentException("wrong index of subTensor");
        }
        return subTensors.get(indices[0]).get(Arrays.copyOfRange(indices, 1, indices.length));
    }

    @Override
    public Tensor<T> getTensor(int... indices) {
        if (indices.length > dim) {
            throw new IllegalArgumentException("wrong size of input");
        } else if (indices.length == dim) {
            return new MinTensor<T>(get(indices));
        } else if (indices.length == 0) {
            return this;
        }
        return subTensors.get(indices[0]).getTensor(Arrays.copyOfRange(indices, 1, indices.length));
    }

    @Override
    public int getDimensional() {
        return dim;
    }
}
