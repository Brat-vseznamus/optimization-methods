package expression;

public class Power implements Expression {
    protected final Expression arg;
    protected final int n;

    public Power(final Expression arg, final int n) {
        if (n != 0) {
            this.arg = arg;
        } else {
            this.arg = Const.ONE;
        }
        this.n = n;
    }

    @Override
    public double evaluate(final double... vars) {
        if (n != 0) {
            return Math.pow(arg.evaluate(vars), n);
        } else return 1;
    }

    @Override
    public Expression diff(final int var) {
        if (n != 0) {
            return new Mul(new Const(n), new Mul(new Power(arg, n - 1), arg.diff(var)));
        } else {
            return Const.ZERO;
        }
    }

    @Override
    public String toPythonStyleString() {
        return toString();
    }
}
