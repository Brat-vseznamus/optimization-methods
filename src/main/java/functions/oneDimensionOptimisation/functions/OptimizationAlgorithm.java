package functions.oneDimensionOptimisation.functions;

import java.util.List;

public interface OptimizationAlgorithm {
    double findMin(double a, double b);

    List<AbstractMethod.Info> getTable();

    List<AbstractMethod.Pair<Double, Integer>> lnToCalculations(double l, double r);

    int getCalculations();
}
