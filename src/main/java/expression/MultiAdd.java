package expression;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MultiAdd implements Expression{
    private final Expression[] args;

    public MultiAdd(final Expression...args) {
        this.args = args;
    }
    @Override
    public double evaluate(final double... vars) {
        return Arrays.stream(args).mapToDouble(a -> a.evaluate(vars)).sum();
    }

    @Override
    public Expression diff(final int var) {
        final Expression[] dargs = new Expression[args.length];
        IntStream.range(0, args.length).forEach(
                i -> dargs[i] = args[i].diff(var)
        );
        return new MultiAdd(dargs);
    }

    @Override
    public String toPythonStyleString() {
        return Arrays.stream(args).map(Expression::toPythonStyleString).collect(Collectors.joining("+"));
    }

    @Override
    public String toString() {
        return "(" + Arrays.stream(args).map(Expression::toString).collect(Collectors.joining("+")) + ")";
    }
}
