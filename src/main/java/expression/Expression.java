package expression;

public interface Expression {

    double evaluate(double... vars);

    Expression diff(int var);

    default String toPythonStyleString() {
        return toString();
    }
}
