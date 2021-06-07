import expression.*;
import linear.DoubleVector;
import newton.ClassicNewtonMethod;
import newton.DescentDirectionNewtonMethod;
import newton.NewtonMethod;
import newton.OneDimOptimizedNewtonMethod;
import newton.utils.FunctionExpression;
import org.junit.jupiter.api.Test;

import java.util.List;

public class SearchingNewtonMethod {
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
    public static final Expression x4 = new X(3);

    public static final FunctionExpression[] functions = {
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

    public static final List<DoubleVector> starts = List.of(
            new DoubleVector(-2d, -2d),
            new DoubleVector(1d, 1d),
            new DoubleVector(4d, 0d),
            new DoubleVector(20d, 20d),
            new DoubleVector(0d, 0d));

    public static void testMethodOnFunction(final NewtonMethod method, final DoubleVector start) {
        final DoubleVector result = method.findMin(start);
        System.out.println(method.getClass().getSimpleName() + ":");
        System.out.println("found: " + result.toString());
//        System.out.println(method.getTable().toString().replace("),", "),\n"));
        System.out.println(method.getTable().size());
        System.out.println();
    }

    public void test(final int n1, final int n2) {
        final FunctionExpression func = functions[n1];
        final DoubleVector start = starts.get(n2);
        System.out.println("Function " + func.toString());
        System.out.println("with start point: " + start.toString());
        for (final NewtonMethod method : newtonMethods) {
            method.setFunction(func);
            testMethodOnFunction(method, start);
        }
    }

    @Test
    public void testAll() {
        for (int n1 = 0; n1 < 1; ++n1) {
            for (int n2 = 0; n2 < 5; ++n2) {
                test(n1, n2);
            }
        }
    }
}
