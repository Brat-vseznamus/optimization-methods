package expression;

import java.util.function.DoubleBinaryOperator;

public abstract class BinaryOperation implements Expression {
    protected final String op;
    protected final DoubleBinaryOperator eval;
    protected final Expression argLeft, argRight;

    protected BinaryOperation(
            final String op, final DoubleBinaryOperator eval,
            final Expression argLeft, final Expression argRight
    ) {
        this.op = op;
        this.eval = eval;
        this.argLeft = argLeft;
        this.argRight = argRight;
    }

    @Override
    public double evaluate(final double... vars) {
        return eval.applyAsDouble(argLeft.evaluate(vars), argRight.evaluate(vars));
    }

    @Override
    public String toString() {
        return String.format("(%s %s %s)", argLeft.toString(), op, argRight.toString());
    }

    @Override
    public String toPythonStyleString() {
        return String.format("(%s %s %s)", argLeft.toPythonStyleString(), op, argRight.toPythonStyleString());
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof BinaryOperation) {
            final BinaryOperation other = (BinaryOperation) obj;
            return op.equals(other.op) && eval.equals(other.eval)
                    && argLeft.equals(other.argLeft) && argRight.equals(other.argRight);
        }
        return false;
    }
}
