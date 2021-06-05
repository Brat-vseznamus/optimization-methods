package expression;

public class Sin extends UnaryOperation{
    public Sin(final Expression arg) {
        super(arg);
    }

    @Override
    public double evaluate(final double... vars) {
        return Math.sin(arg.evaluate(vars));
    }

    @Override
    public Expression diff(final int var) {
        return new Mul(arg.diff(var), new Cos(arg));
    }

    @Override
    public String toString() {
        return "sin(" + arg.toString() + ")";
    }

    @Override
    public String toPythonStyleString() {
        return "np.sin(" + arg.toPythonStyleString() + ")";
    }
}
