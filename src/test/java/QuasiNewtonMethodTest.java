import expression.*;
import linear.DoubleVector;
import newton.ClassicNewtonMethod;
import newton.DescentDirectionNewtonMethod;
import newton.NewtonMethod;
import newton.OneDimOptimizedNewtonMethod;
import newton.utils.FunctionExpression;
import org.junit.jupiter.api.Test;

public class QuasiNewtonMethodTest {
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

    public static final FunctionExpression[] functionsTest1 = {
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
                            new Square(new Sub(Const.one, x1))
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
    public void test1() {
        final FunctionExpression func = functionsTest1[0];
        final DoubleVector start = new DoubleVector(4d, 1d);
        System.out.println("Function " + func.toString());
        System.out.println("with start point: " + start.toString());
        for (final NewtonMethod method : newtonMethods) {
            method.setFunction(func);
            testMethodOnFunction(method, start);
        }
    }

    @Test
    public void test2() {
        final FunctionExpression func = functionsTest1[1];
        final DoubleVector start = new DoubleVector(-1.2d, 1d);
        System.out.println("Function " + func.toString());
        System.out.println("with start point: " + start.toString());
        for (final NewtonMethod method : newtonMethods) {
            method.setFunction(func);
            testMethodOnFunction(method, start);
        }
    }
}
