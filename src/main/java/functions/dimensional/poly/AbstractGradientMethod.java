package functions.dimensional.poly;

import functions.Pair;

import java.util.List;
import java.util.ArrayList;

public abstract class AbstractGradientMethod implements GradientOptimizationMethod {
    protected QuadraticForm form;
    protected double eps;
    protected List<State> table;

    protected AbstractGradientMethod(final QuadraticForm form, final double eps) {
        this.form = form;
        this.eps = eps;
        table = new ArrayList<>();
    }

    protected AbstractGradientMethod(final QuadraticForm form) {
        this(form, 1e-5d);
    }

    public List<State> getTable() {
        return table;
    }

    @Override
    public List<Pair<Integer, List<Pair<Integer, Integer>>>> valueAndDimToIterations() {
        final int[] values = new int[]{1, 2, 5, 10, 20, 30, 50, 100};
        final int[] dimensions = new int[]{2, 5, 10, 20, 30, 40, 50, 100, 200, 300, 400, 500};
//        final int[] values = new int[]{1, 2, 5};
//        final int[] dimensions = new int[]{2, 5, 10};
        final List<Pair<Integer, List<Pair<Integer, Integer>>>> result = new ArrayList<>(values.length);
        for (final int value : values) {
            result.add(new Pair<>(value, new ArrayList<>(dimensions.length)));
            for (final int dim : dimensions) {
                final QuadraticForm form = FormGenerator.generate(dim, value);
                table.clear();
                findMin();
                result.get(result.size() - 1).second.add(new Pair<>(dim, table.size()));
            }
        }

        return result;
    }

    public static class State {
        private final DoubleVector point;
        private final double value;

        public State(final DoubleVector point, final double value) {
            this.point = point;
            this.value = value;
        }

        public DoubleVector getPoint() {
            return point;
        }

        public double getValue() {
            return value;
        }
    }
}
