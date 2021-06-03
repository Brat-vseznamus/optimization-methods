package newton;

import expression.*;
import linear.DoubleVector;
import newton.utils.FunctionExpression;

public class Main {
    public static final NewtonMethod classic = new ClassicNewtonMethod();
    public static final NewtonMethod descend = new DescentDirectionNewtonMethod();
    public static final NewtonMethod oneDim = new OneDimOptimizedNewtonMethod();

    public static final NewtonMethod[] newtonMethods = {
            classic,
            descend,
            oneDim
    };

    public static final Expression x1 = new X(1);
    public static final Expression x2 = new X(2);

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

    public static void testMethodOnFunction(final NewtonMethod method, final int no, final DoubleVector start) {
        final DoubleVector result = method.findMin(start);
        System.out.println(result.getClass().getSimpleName() + ":");
        System.out.println("found: " + result.toString());
        System.out.println();
    }

    public static void test1() {
        final FunctionExpression func = functionsTest1[0];
        final DoubleVector start = new DoubleVector(4d, 1d);
        System.out.println("Function " + func.toString());
        System.out.println("with start point: " + start.toString());
        for (final NewtonMethod method : newtonMethods) {
            method.setFunction(func);
            testMethodOnFunction(method, 0, start);
        }
    }

    public static void main(final String[] args) {
        test1();
    }
}
