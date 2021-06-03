import expression.*;
import linear.DoubleVector;
import linear.Matrices;
import newton.ClassicNewtonMethod;
import newton.DescentDirectionNewtonMethod;
import newton.NewtonMethod;
import newton.OneDimOptimizedNewtonMethod;
import newton.quasi.BFSQuasiNewtonMethod;
import newton.quasi.PaulleQuasiNewtonMethod;
import newton.quasi.QuasiNewtonMethod;
import newton.utils.FunctionExpression;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

public class QuasiNewtonMethodTest {
    public static final QuasiNewtonMethod bfs = new BFSQuasiNewtonMethod();
    public static final QuasiNewtonMethod paulle = new PaulleQuasiNewtonMethod();

    public static final NewtonMethod[] quasiNewtonMethods = {
            bfs,
            paulle
    };

    public static final Expression x1 = new X(0);
    public static final Expression x2 = new X(1);
    public static final Expression x3 = new X(2);
    public static final Expression x4 = new X(3);

    public static final FunctionExpression[] functions = {
            new FunctionExpression(
                    new Add(
                            new Mul(
                                    new Const(100),
                                    new Square(new Sub(x2, new Square(x1)))),
                            new Square(new Sub(Const.ONE, x1))
                    ),
                    2,
                    false
            ),
            new FunctionExpression(
                    new Add(
                            new Square(
                                    new Sub(
                                            new Add(new Square(x1), x2),
                                            new Const(11d)
                                    )
                            ),
                            new Square(
                                    new Sub(
                                            new Add(x1, new Square(x2)),
                                            new Const(7d)
                                    )
                            )
                    ),
                    2,
                    false
            ),
            new FunctionExpression(
                    new Add(
                            new Square(
                                    new Add(
                                            x1,
                                            new Mul(new Const(10d), x2)
                                    )
                            ),
                            new Add(
                                    new Mul(
                                            Const.FIVE,
                                            new Square(new Sub(x3, x4))),
                                    new Add(
                                            new Square(new Square(new Sub(x2, new Mul(new Const(2d), x3)))),
                                            new Mul(new Const(10d), new Square(new Square(new Sub(x1, x4))))
                                    )
                            )
                    ),
                    4,
                    false
            ),
            new FunctionExpression(
                    new Sub(
                            new Sub(
                                    new Const(100d),
                                    new Div(
                                            Const.TWO,
                                            new Add(
                                                    new Add(
                                                            Const.ONE,
                                                            new Square(
                                                                    new Div(
                                                                            new Sub(x1, Const.ONE),
                                                                            Const.TWO
                                                                    )
                                                            )
                                                    ),
                                                    new Square(
                                                            new Div(
                                                                    new Sub(x2, Const.ONE),
                                                                    Const.THREE
                                                            )
                                                    )
                                            )
                                    )),
                            new Div(
                                    Const.ONE,
                                    new Add(
                                            new Add(
                                                    Const.ONE,
                                                    new Square(new Div(new Sub(x1, Const.TWO), Const.TWO))
                                            ),
                                            new Square(new Div(new Sub(x2, Const.ONE), Const.THREE))
                                    )
                            )

                    ),
                    2,
                    false
            )
    };

    public static final double[] answers = {
            0d,
            0d,
            0d,
            97.1531d
    };

    public static void testMethodOnFunction(final NewtonMethod method, final FunctionExpression func, final double ans) {
        method.setFunction(func);
        final DoubleVector result = method.findMin(new DoubleVector(func.getN()));
        System.out.println(method.getClass().getSimpleName() + ":");
        System.out.println("found: " + result.toString());

//        System.out.println(method.getTable().toString().replace("),", "),\n"));
        System.out.println("iterations: " + method.getTable().size());

        System.out.println();

        Assertions.assertTrue(Matrices.epsEquals(func.evaluate(result.toArray()), ans));
    }

    public void test(final FunctionExpression func, final double ans) {
        System.out.println("FUNCTION " + func.toString());
        for (final NewtonMethod method : quasiNewtonMethods) {
            testMethodOnFunction(method, func, ans);
        }
    }

    @Test
    public void test() {
        IntStream.range(0, functions.length).forEach(i -> test(functions[i], answers[i]));
    }
}
