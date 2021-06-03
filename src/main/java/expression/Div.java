package expression;

public class Mul extends BinaryOperation {
    public Mul(final Expression argLeft, final Expression argRight) {
        super("*", (a, b) -> a * b, argLeft, argRight);
    }

    @Override
    public Expression diff() {
        return new Mul(
                new Add(argLeft.diff(), argRight),
                new Add(argLeft, argRight.diff())
        );
    }
}
