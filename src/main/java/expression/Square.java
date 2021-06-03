package expression;

public class Square implements Expression {
    protected final Expression arg;

    public Square(final Expression arg) {
        this.arg = arg;
    }

    @Override
    public double evaluate(final double... vars) {
        final double res = arg.evaluate(vars);
        return res * res;
    }

    @Override
    public Expression diff(final int var) {
        return new Mul(new Mul(Const.two, arg), arg.diff(var));
    }

    @Override
    public String toString() {
        return String.format("(%s)^2", arg.toString());
    }

    @Override
    public boolean equals(final Object obj) {
        return obj instanceof Square && arg.equals(((Square) obj).arg);
    }
}
