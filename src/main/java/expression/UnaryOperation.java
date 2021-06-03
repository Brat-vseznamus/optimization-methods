package expression;

import java.util.function.Function;

public abstract class UnaryOperation implements Expression {
    protected Expression arg;

    public UnaryOperation(Expression arg) {
        this.arg = arg;
    }
}
