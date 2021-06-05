package expression;

public abstract class UnaryOperation implements Expression {
    protected Expression arg;

    public UnaryOperation(final Expression arg) {
        this.arg = arg;
    }

    @Override
    public String toPythonStyleString() {
        return toString();
    }
}
