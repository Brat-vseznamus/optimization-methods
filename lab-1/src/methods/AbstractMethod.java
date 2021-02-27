package methods;

import java.util.function.Function;

public abstract class AbstractMethod implements OptimizationAlgorithm{
    protected Function<Double, Double> function;

    public AbstractMethod(Function<Double, Double> function) {
        this.function = function;
    }
    
}
