package methods.dimensional.one;

import java.util.List;
import java.util.ArrayList;
import java.util.function.UnaryOperator;

import methods.Pair;

public abstract class AbstractOneDimensionalMethod implements DrawableMethod {
    protected final static double DEFAULT_EPS = 1e-5d;
    protected UnaryOperator<Double> function;
    protected List<Info> table;
    protected double eps;
    protected int calculations = 0;

    protected AbstractOneDimensionalMethod(final UnaryOperator<Double> function, final double eps) {
        this.function = function;
        this.eps = eps;
        table = new ArrayList<>();
    }

    protected AbstractOneDimensionalMethod(final double eps) {
        this(null, eps);
    }

    protected AbstractOneDimensionalMethod(final UnaryOperator<Double> function) {
        this(function, DEFAULT_EPS);
    }

    public UnaryOperator<Double> getFunction() {
        return function;
    }

    public void setFunction(final UnaryOperator<Double> function) {
        this.function = function;
    }

    @Override
    public int getCalculations() {
        return calculations;
    }

    @Override
    public List<Info> getTable() {
        return table;
    }

    public void addInfo(final double l, final double r, final double value) {
        table.add(new Info(l, r, value));
    }

    public void setEps(final double eps) {
        this.eps = eps;
    }

    public int getCalculationCount() {
        return table.size();
    }

    public static class Info {
        private final double left, right, value;

        public Info(final double left, final double right, final double value) {
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

    public List<Pair<Double, Integer>> lnToCalculations(final double l, final double r) {
        final List<Pair<Double, Integer>> values = new ArrayList<>();
        final double epsOld = eps;
        eps = 1;
        for (int i = 0; i < 10; i++) {
            eps /= 10d;
            table = new ArrayList<>();
            findMin(l, r);
            values.add(new Pair<>(-Math.log10(eps), getCalculations()));
        }
        eps = epsOld;
        return values;
    }

}