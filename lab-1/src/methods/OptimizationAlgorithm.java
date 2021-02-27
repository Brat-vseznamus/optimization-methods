package methods;

import java.util.List;
import java.util.function.Function;

public interface OptimizationAlgorithm {
    double findMin(double a, double b);
    List<AbstractMethod.Info> getTable();
}
