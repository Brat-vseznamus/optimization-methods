package expression;

public class Div extends BinaryOperation {
    public Div(final Expression argLeft, final Expression argRight) {
        super("/", (a, b) -> a * b, argLeft, argRight);
    }

    @Override
    public Expression diff(final int var) {
        return new Div(
                new Add(
                        new Mul(argLeft.diff(var), argRight),
                        new Mul(argLeft, argRight.diff(var))
                ),
                new Mul(argRight, argRight)
        );
    }
}
