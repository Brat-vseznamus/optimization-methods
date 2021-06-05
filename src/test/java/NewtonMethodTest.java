import expression.*;
import linear.DoubleVector;
import linear.Matrices;
import newton.ClassicNewtonMethod;
import newton.DescentDirectionNewtonMethod;
import newton.NewtonMethod;
import newton.OneDimOptimizedNewtonMethod;
import newton.utils.FunctionExpression;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

public class NewtonMethodTest {
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

    public static final FunctionExpression[] functions = {
            new FunctionExpression(
                    new Sub(
                            new Add(
                                    new Square(x1),
                                    new Square(x2)
                            ),
                            new Mul(new Const(1.2d), new Mul(x1, x2))
                    ),
                    2,
                    true
            ),
            new FunctionExpression(
                    new Add(
                            new Mul(
                                    new Const(100),
                                    new Square(new Sub(x2, new Square(x1)))),
                            new Square(new Sub(Const.ONE, x1))
                    ),
                    2,
                    true
            )
    };

    public static final double[] answers = {
            0d,
            0d
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
        for (final NewtonMethod method : newtonMethods) {
            testMethodOnFunction(method, func, ans);
        }
    }


    @Test
    public void test() {
        IntStream.range(0, functions.length).forEach(i -> test(functions[i], answers[i]));
    }
}
