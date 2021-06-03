package expression;

public class Mul extends BinaryOperation {
    public Mul(final Expression argLeft, final Expression argRight) {
        super("*", (a, b) -> a * b, argLeft, argRight);
    }

    @Override
    public Expression diff(final int var) {
        return new Add(
                new Mul(argLeft.diff(var), argRight),
                new Mul(argLeft, argRight.diff(var))
        );
    }
}
