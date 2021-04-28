package methods.dimensional.one;

import java.util.List;
import java.util.function.UnaryOperator;

import methods.Pair;

public interface OneDimensionalOptimizationMethod {
    void setFunction(final UnaryOperator<Double> function);

    double findMin(double a, double b);

    List<AbstractOneDimensionalMethod.Info> getTable();

    List<Pair<Double, Integer>> lnToCalculations(double l, double r);

    int getCalculations();
}
