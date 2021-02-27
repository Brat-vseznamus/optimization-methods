package methods;

import java.util.function.Function;

public interface OptimizationAlgorithm {
    double findMin(Function<Double, Double> function, double a, double b);
}
