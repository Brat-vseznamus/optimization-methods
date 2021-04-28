package functions.manydimensional;

import java.util.List;

public interface OptimisationGradientMethod {
    double[] findMin();
    List<AbstractGradientMethod.State> getTable();
    // List<Pair;
}
