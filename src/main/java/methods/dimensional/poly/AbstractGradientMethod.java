package methods.dimensional.poly;

import methods.Pair;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractGradientMethod implements GradientOptimizationMethod {
    protected final static double DEFAULT_EPS = 1e-5d;
    protected final double eps;
    protected final List<State> table;
    protected QuadraticForm form;

    protected AbstractGradientMethod(final QuadraticForm form, final double eps) {
        this.form = form;
        this.eps = eps;
        table = new ArrayList<>();
    }

    protected AbstractGradientMethod(final QuadraticForm form) {
        this(form, DEFAULT_EPS);
    }

    public AbstractGradientMethod() {
        this(null);
    }

    public QuadraticForm getForm() {
        return form;
    }

    public void setForm(final QuadraticForm form) {
        this.form = form;
    }

    public List<State> getTable() {
        return table;
    }

    @Override
    public List<Pair<Integer, List<Pair<Integer, Integer>>>> valueAndDimToIterations() {
        final int[] values = new int[]{2, 5, 10, 20, 50, 100, 300/*, 500, 1000*/};
        final int[] dimensions = new int[]{2, 5, 10, 20, 30, 40, 50, 100, 200, 500, 1000/*, 2000, 5000*/};
        final List<Pair<Integer, List<Pair<Integer, Integer>>>> result = new ArrayList<>(values.length);
        for (final int value : values) {
            result.add(new Pair<>(value, new ArrayList<>(dimensions.length)));
            for (final int dim : dimensions) {
//                try {
                    form = FormGenerator.generate(dim, value);
                    table.clear();
                    findMin();
                    result.get(result.size() - 1).second.add(new Pair<>(dim, table.size()));
//                } catch (final Exception e) {
//                    System.out.println("Exception while calculating table.");
//                    return result;
//                }
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
