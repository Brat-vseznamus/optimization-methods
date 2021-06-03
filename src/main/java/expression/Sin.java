package expression;

public class Sin extends UnaryOperation{
    public Sin(Expression arg) {
        super(arg);
    }

    @Override
    public double evaluate(double... vars) {
        return Math.sin(arg.evaluate(vars));
    }

    @Override
    public Expression diff(int var) {
        return new Mul(arg.diff(var), new Cos(arg));
    }
}
