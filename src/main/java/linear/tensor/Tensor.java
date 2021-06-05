package linear.tensor;

public interface Tensor<T> {
    T get(int...indices);
    Tensor<T> getTensor(int...indices);
    int getDimensional();
}
