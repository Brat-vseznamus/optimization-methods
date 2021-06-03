package expression;

public class X implements Expression {
    private final char var;

    public X(final char var) {
        this.var = var;
    }

    @Override
    public double evaluate(final double... vars) {
        // TODO
        return vars[0];
    }

    @Override
    public Expression diff() {
        return Const.one;
    }
}
