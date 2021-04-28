package methods.dimensional.poly;

import methods.Pair;

import java.util.List;

public interface GradientOptimizationMethod {
    public void setForm(final QuadraticForm form);

    double[] findMin();

    List<AbstractGradientMethod.State> getTable();

    List<Pair<Integer, List<Pair<Integer, Integer>>>> valueAndDimToIterations();
}
