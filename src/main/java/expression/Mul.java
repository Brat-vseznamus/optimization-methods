package expression;

public class Sub extends BinaryOperation {
    public Sub(final Expression argLeft, final Expression argRight) {
        super("-", (a, b) -> a - b, argLeft, argRight);
    }

    @Override
    public Expression diff() {
        return new Sub(argLeft.diff(), argRight.diff());
    }
}
