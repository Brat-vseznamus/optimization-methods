package functions;

import java.util.function.Function;

public class NFunction {
    private int dims;
    private Function<Double[], Double> fun;
    NFunction(int dims, Function<Double[], Double> fun) {
        this.dims = dims;
        this.fun = fun;
    }
    
    public double apply(Double...doubles) {
        if (doubles.length != dims) {
            return 0;
        }
        return fun.apply(doubles);
    }
}
