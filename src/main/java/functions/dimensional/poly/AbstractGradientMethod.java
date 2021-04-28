package functions.dimensional.poly;

import java.util.List;
import java.util.ArrayList;

public abstract class AbstractGradientMethod implements OptimisationGradientMethod {
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

    public static class State {
        private final DoubleVector point;
        private final double value;

        public State(DoubleVector point, double value) {
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
