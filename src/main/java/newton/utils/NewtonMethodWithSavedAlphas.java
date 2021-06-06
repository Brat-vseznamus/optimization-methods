package newton.utils;

import java.util.ArrayList;
import java.util.List;

public interface NewtonMethodWithSavedAlphas {
    List<Double> alphas = new ArrayList<>();

    default List<Double> getAlphas() {
        return alphas;
    }

    default void addAlpha(final double alpha) {
        alphas.add(alpha);
    }

    default void clearAlphas() {
        alphas.clear();
    }

}
