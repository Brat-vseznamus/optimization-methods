package methods;

import java.util.List;
import java.util.function.Function;

public abstract class AbstractMethod implements OptimizationAlgorithm{
    protected Function<Double, Double> function;
    protected List<Info> table;

    @Override
    public List<Info> getTable() {
        return table;
    }

    public AbstractMethod(Function<Double, Double> function) {
        this.function = function;
    }

    public static class Info {
        private final double left, right, value;

        public Info(double left, double right, double value) {
            this.left = left;
            this.right = right;
            this.value = value;
        }

        public double getLeft() {
            return left;
        }

        public double getRight() {
            return right;
        }

        public double getValue() {
            return value;
        }
    }
}
