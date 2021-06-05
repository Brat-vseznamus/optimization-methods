package expression;

public class Cos extends UnaryOperation {

    public Cos(final Expression arg) {
        super(arg);
    }

    @Override
    public double evaluate(final double... vars) {
        return Math.cos(arg.evaluate(vars));
    }

    @Override
    public Expression diff(final int var) {
        return new Mul(arg.diff(var), new Mul(new Const(-1), new Sin(arg)));
    }

    @Override
    public String toString() {
        return "cos(" + arg.toString() + ")";
    }

    @Override
    public String toPythonStyleString() {
        return "np.cos(" + arg.toPythonStyleString() + ")";
    }
}
