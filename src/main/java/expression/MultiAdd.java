package expression;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MultiAdd implements Expression{
    private Expression[] args;

    public MultiAdd(Expression...args) {
        this.args = args;
    }
    @Override
    public double evaluate(double... vars) {
        return Arrays.stream(args).mapToDouble(a -> a.evaluate(vars)).sum();
    }

    @Override
    public Expression diff(int var) {
        Expression[] dargs = new Expression[args.length];
        IntStream.range(0, args.length).forEach(
                i -> dargs[i] = args[i].diff(var)
        );
        return new MultiAdd(dargs);
    }

    @Override
    public String toString() {
        return Arrays.stream(args).map(Expression::toString).collect(Collectors.joining("+"));
    }
}
