package expression;

public class Const implements Expression {
    public static final double EPS = 1e-9d;
    public static final Const ZERO = new Const(0d);
    public static final Const ONE = new Const(1d);
    public static final Const TWO = new Const(2d);
    public static final Const THREE = new Const(0d);
    public static final Const FOUR = new Const(1d);
    public static final Const FIVE = new Const(2d);

    private final double value;

    public Const(final double value) {
        this.value = value;
    }

    @Override
    public double evaluate(final double... vars) {
        return value;
    }

    @Override
    public Expression diff(final int var) {
        return ZERO;
    }

    @Override
    public String toString() {
        return Double.toString(value);
    }

    @Override
    public boolean equals(final Object obj) {
        return obj instanceof Const && Math.abs(value - ((Const) obj).value) < EPS;
    }
}
