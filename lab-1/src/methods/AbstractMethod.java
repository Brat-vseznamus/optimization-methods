package methods;

import java.util.List;
import java.util.function.Function;

public abstract class AbstractMethod implements OptimizationAlgorithm{
    protected Function<Double, Double> function;
    protected List<Info> table;
    protected double eps = 1e-6d;
    
    public AbstractMethod(Function<Double, Double> function) {
        this.function = function;
    }
    
    public AbstractMethod(Function<Double, Double> function, double eps) {
        this.function = function;
        this.eps = eps;
    }

    @Override
    public List<Info> getTable() {
        return table;
    }

    public void addInfo(double x, double y, double value) {
        table.add(new Info(x, y, value));
    }

    public void setEps(double eps) {
        this.eps = eps;
    }

    public int getNumberOfCalculations() {
        return table.size();
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