package newton.utils;

import expression.Expression;
import linear.DoubleMatrix;
import linear.DoubleVector;
import linear.Matrix;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FunctionExpression implements Expression {
    private final Expression expression;
    private final int n;
    private List<Expression> firstDiffs;
    private List<List<Expression>> secondsDiffs;

    public FunctionExpression(final Expression expression, final int n, final boolean cnt2ndDiffs) {
        this.expression = expression;
        this.n = n;
        calculateFirstDiffs();
        if (cnt2ndDiffs) {
            calculateSecondDiffs();
        } else {
            secondsDiffs = null;
        }
    }

    public FunctionExpression(final Expression expression, final int n) {
        this(expression, n, true);
    }

    public void calculateFirstDiffs() {
        firstDiffs = IntStream.range(0, n)
                .mapToObj(expression::diff)
                .collect(Collectors.toList());
    }

    public void calculateSecondDiffs() {
        secondsDiffs = IntStream.range(0, n)
                .mapToObj(i ->
                        firstDiffs.stream()
                                .map(df -> df.diff(i))
                                .collect(Collectors.toList())
                )
                .collect(Collectors.toList());
    }

    @Override
    public double evaluate(final double... vars) {
        final double[] values = Arrays.copyOf(vars, n);
        return expression.evaluate(values);
    }

    @Override
    public Expression diff(final int var) {
        if (var < 0 || var > n) {
            throw  new IllegalArgumentException("illegal number of variable");
        }
        return expression.diff(var);
    }

    @Override
    public String toPythonStyleString() {
        return expression.toPythonStyleString();
    }

    public DoubleVector gradient(final DoubleVector vars) {
        final double[] values = vars.toArray();
        return new DoubleVector(
            firstDiffs
                .stream()
                .mapToDouble(df -> df.evaluate(values))
                .toArray()
        );
    }

    public DoubleMatrix hessian(final DoubleVector vars) {
        final double[] values = vars.toArray();
        final double[][] data = new double[n][n];
        IntStream.range(0, n).forEach(
            i -> IntStream.range(0, n).forEach(
                j -> data[i][j] =
                        secondsDiffs
                            .get(i).get(j)
                            .evaluate(values)
            )
        );
        return new DoubleMatrix(data);
    }

    public int getN() {
        return n;
    }

    @Override
    public String toString() {
        return expression.toString();
    }
}
