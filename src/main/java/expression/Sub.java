package expression;

public class Add extends BinaryOperation {
    public Add(final Expression argLeft, final Expression argRight) {
        super("+", Double::sum, argLeft, argRight);
    }

    @Override
    public Expression diff() {
        return new Add(argLeft.diff(), argRight.diff());
    }
}
