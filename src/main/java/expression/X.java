package expression;

public class X implements Expression {
    private final int no;

    public X(final int no) {
        this.no = no;
    }

    @Override
    public double evaluate(final double... vars) {
        return vars[no];
    }

    @Override
    public Expression diff(final int var) {
        return var == no ? Const.ONE : Const.ZERO;
    }

    @Override
    public String toPythonStyleString() {
        return toString();
    }

    @Override
    public String toString() {
        return "x" + (no + 1);
    }

    @Override
    public boolean equals(final Object obj) {
        return obj instanceof X && no == ((X) obj).no;
    }
}
