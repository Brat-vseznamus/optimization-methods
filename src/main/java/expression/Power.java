package expression;

import java.util.stream.IntStream;

public class Power implements Expression {
    protected final Expression arg;
    protected final int n;

    public Power(Expression arg, int n) {
        if (n != 0) {
            this.arg = arg;
        } else {
            this.arg = Const.ONE;
        }
        this.n = n;
    }

    @Override
    public double evaluate(double... vars) {
        if (n != 0) {
            return Math.pow(arg.evaluate(vars), n);
        } else return 1;
    }

    @Override
    public Expression diff(int var) {
        if (n != 0) {
            return new Mul(new Const(n), new Power(arg, n - 1));
        } else {
            return Const.ZERO;
        }
    }
}
