package linear.tensor;

public class MinTensor<T> implements Tensor<T> {
    private final T x;

    public MinTensor(T x) {
        this.x = x;
    }

    @Override
    public T get(int... indices) {
        return x;
    }

    @Override
    public Tensor<T> getTensor(int... indices) {
        if (indices.length != 0) {
            throw new IllegalArgumentException("wrong size");
        }
        return this;
    }

    @Override
    public int getDimensional() {
        return 0;
    }

    @Override
    public String toString() {
        return x.toString();
    }
}
