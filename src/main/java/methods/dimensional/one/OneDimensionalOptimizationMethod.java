package methods.dimensional.one;

import java.util.List;
import methods.Pair;

public interface OneDimensionalOptimizationMethod {
    double findMin(double a, double b);

    List<AbstractOneDimensionalMethod.Info> getTable();

    List<Pair<Double, Integer>> lnToCalculations(double l, double r);

    int getCalculations();
}
