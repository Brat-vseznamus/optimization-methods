package functions;

import java.util.List;
import java.util.ArrayList;
import java.util.function.UnaryOperator;

public abstract class AbstractMethod implements DrawableOptimizationAlgorithm{
    protected UnaryOperator<Double> function;
    protected List<Info> table;
    protected double eps;
    
    protected AbstractMethod(UnaryOperator<Double> function, double eps) {
        this.function = function;
        this.eps = eps;
        table = new ArrayList<>();
    }

    protected AbstractMethod(UnaryOperator<Double> function) {
        this(function, 1e-9d);
    }
    
    @Override
    public List<Info> getTable() {
        return table;
    }

    public void addInfo(double l, double r, double value) {
        table.add(new Info(l, r, value));
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

        @Override
        public String toString() {
            return String.format("\n<a: %.10f, b: %.10f, mid: %.10f>", left, right, value);
        }
    }
    
}