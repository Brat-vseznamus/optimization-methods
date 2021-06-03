package expression;

public class Cos extends UnaryOperation {

    public Cos(Expression arg) {
        super(arg);
    }

    @Override
    public double evaluate(double... vars) {
        return Math.cos(arg.evaluate(vars));
    }

    @Override
    public Expression diff(int var) {
        return new Mul(arg.diff(var), new Mul(new Const(-1), new Sin(arg)));
    }
}
