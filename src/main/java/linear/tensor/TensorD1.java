package linear.tensor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TensorD1<T> extends TensorImpl<T>{
    public TensorD1(T...values) {
        super(Arrays.stream(values).map(MinTensor::new).collect(Collectors.toList()));
    }

    @Override
    public String toString() {
        return Arrays.toString(subTensors.toArray());
    }
}
