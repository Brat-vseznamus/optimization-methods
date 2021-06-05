package linear.tensor;

import java.util.Arrays;
import java.util.stream.Collectors;

public class TMatrix<T> extends TensorD2<T>{
    public TMatrix(T[][] values) {
        super(Arrays.stream(values)
                .map(TensorD1::new)
                .collect(Collectors.toList()));
    }
}
