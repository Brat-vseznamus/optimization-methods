package functions;

import java.util.List;

public interface OptimisationGradientMethod {
    double[] findMin();
    List<AbstractGradientMethod.State> getTable();
}
