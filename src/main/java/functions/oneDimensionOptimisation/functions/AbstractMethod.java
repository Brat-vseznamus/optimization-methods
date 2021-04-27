package functions.oneDimensionOptimisation.functions;

import java.util.List;
import java.util.ArrayList;
import java.util.function.UnaryOperator;

public abstract class AbstractMethod implements DrawableOptimizationAlgorithm {
    protected UnaryOperator<Double> function;
    protected List<Info> table;
    protected double eps;
    protected int calcs = 0;

    protected AbstractMethod(UnaryOperator<Double> function, double eps) {
        this.function = function;
        this.eps = eps;
        table = new ArrayList<>();
    }

    @Override
    public int getCalculations() {
        return calcs;
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

    public int getCalculationCount() {
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

    public static class Pair<T, E> {
        public T a;
        public E b;
        Pair(T a, E b) {
            this.a = a;
            this.b = b;
        }
    }


    public List<Pair<Double, Integer>> lnToCalculations(double l, double r) {
        List<Pair<Double, Integer>> values = new ArrayList<>();
        double epsOld = eps;
        eps = 1;
        for (int i = 0; i < 10; i++) {
            eps /= 10d;
            table = new ArrayList<>();
            // System.out.println("value=" + findMin(l, r));
            findMin(l, r);
            values.add(new Pair<>(-Math.log10(eps), (int)getCalculations()));
        }
        eps = epsOld;
        return values;
    }

}