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
}
