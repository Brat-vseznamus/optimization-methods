package methods;

import java.util.List;

public interface OptimizationAlgorithm {
    double findMin(double a, double b);
    List<AbstractMethod.Info> getTable();
}
