import expression.*;
import linear.DoubleVector;
import newton.ClassicNewtonMethod;
import newton.DescentDirectionNewtonMethod;
import newton.NewtonMethod;
import newton.OneDimOptimizedNewtonMethod;
import newton.utils.FunctionExpression;
import org.junit.jupiter.api.Test;

public class OurFunctionsNewtonMethod {
    public static final NewtonMethod classic = new ClassicNewtonMethod();
    public static final NewtonMethod descend = new DescentDirectionNewtonMethod();
    public static final NewtonMethod oneDim = new OneDimOptimizedNewtonMethod();

    public static final NewtonMethod[] newtonMethods = {
            classic,
            descend,
            oneDim
    };

    public static final Expression x1 = new X(0);
    public static final Expression x2 = new X(1);
    public static final Expression x3 = new X(2);

    public static final FunctionExpression[] functionsTest1 = {
            new FunctionExpression(
                    new Add(
                        new Square(x1),
                        new Add(
                            new Square(x2),
                            new Mul(
                                    new Const(2),
                                    new Mul(x1, x2)
                            )
                        )
                    ),
                    2,
                    true
            ),
            new FunctionExpression(
                    new Mul(
                        new Cos(
                            new Mul(
                                new Add(
                                       new Square(x1),
                                       new Add(
                                           new Square(x2),
                                           new Power(x3, 4)
                                       )
                                ),
                                new Const(0.04)
                            )
                        ),
                        new Const(-4)
                    ),
                    3,
                    true
            ),
            new FunctionExpression(
                    new Mul(
                            new Cos(
                                    new Mul(
                                            new Add(
                                                    new Square(x1),
                                                    new Add(
                                                            Const.ONE,
                                                            new Power(x2, 4)
                                                    )
                                            ),
                                            new Const(0.04)
                                    )
                            ),
                            new Const(-4)
                    ),
                    2,
                    true
            )
    };

    public static void testMethodOnFunction(final NewtonMethod method, final DoubleVector start) {
        final DoubleVector result = method.findMin(start);
        System.out.println(method.getClass().getSimpleName() + ":");
        System.out.println("found: " + result.toString());
        System.out.println(method.getTable().toString().replace("),", "),\n"));
        System.out.println();
    }

    @Test
    public void testForUnsolvableForGauss() {
        test(0, new DoubleVector(-1, 2));
    }

    @Test
    public void testForNonQuadratic() {
        test(1, new DoubleVector(-2, -2, -1));
    }

    @Test
    public void testForNonQuadratic2() {
        test(2, new DoubleVector(-2, -2));
    }

    public void test(final int n, final DoubleVector x0) {
        final FunctionExpression func = functionsTest1[n];
        final DoubleVector start = x0;
        System.out.println("Function " + func.toString());
        System.out.println("with start point: " + start.toString());
        for (final NewtonMethod method : newtonMethods) {
            method.setFunction(func);
            testMethodOnFunction(method, start);
        }
    }
}
