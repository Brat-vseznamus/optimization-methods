package methods.dimensional.poly;

import methods.Pair;

import java.util.List;

public interface GradientOptimizationMethod {
    double[] findMin();

    List<AbstractGradientMethod.State> getTable();

    List<Pair<Integer, List<Pair<Integer, Integer>>>> valueAndDimToIterations();
}
