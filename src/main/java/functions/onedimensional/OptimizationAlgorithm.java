package functions.onedimensional;

import java.util.List;
import functions.Pair;

public interface OptimizationAlgorithm {
    double findMin(double a, double b);

    List<AbstractMethod.Info> getTable();

    List<Pair<Double, Integer>> lnToCalculations(double l, double r);

    int getCalculations();
}
