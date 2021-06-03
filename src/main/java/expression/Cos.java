package expression;

public class Cos implements Expression {

    public Cos(Expression arg) {

    }
    @Override
    public double evaluate(double... vars) {
        return 0;
    }

    @Override
    public Expression diff(int var) {
        return null;
    }
}
