package functions.dimensional.poly;

import functions.Pair;

import java.util.List;

public interface GradientOptimizationMethod {
    double[] findMin();

    List<AbstractGradientMethod.State> getTable();

    List<Pair<Integer, List<Pair<Integer, Integer>>>> valueAndDimToIterations();
}
