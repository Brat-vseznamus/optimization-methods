package functions.dimensional.poly;

import java.util.List;

public interface GradientOptimizationMethod {
    double[] findMin();
    List<AbstractGradientMethod.State> getTable();
}
