package linear.tensor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TensorD2<T> extends TensorImpl<T>{

    @SafeVarargs
    public TensorD2(TensorD1<T>...vectors) {
        super(vectors);
    }

    public TensorD2(List<TensorD1<T>> vectors) {
        super(vectors);
    }

    @SafeVarargs
    public TensorD2(Tensor<T>...vectors) {
        super(vectors);
        if (!Arrays.stream(vectors).allMatch(t -> t instanceof TensorD1)) {
            throw new IllegalArgumentException("wrong types");
        }
    }

    @Override
    public String toString() {
        return "[" + subTensors.stream()
                .map(Tensor::toString)
                .collect(Collectors.joining(",\n"))
                + "]";
    }
}
