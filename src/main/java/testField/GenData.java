package testField;

import expression.*;
import linear.DoubleVector;
import newton.DescentDirectionNewtonMethod;
import newton.NewtonMethod;
import newton.utils.FunctionExpression;
import newton.utils.Iteration;

import java.util.List;

public class GenData {
    public static void main(String[] args) {
        X x1 = new X(0);
        X x2 = new X(1);
        FunctionExpression f = new FunctionExpression(
                new Add(
                        new Mul(
                                new Const(100),
                                new Square(new Sub(x2, new Square(x1)))),
                        new Square(new Sub(Const.ONE, x1))
                ),
                2,
                true
        );
        final NewtonMethod descend = new DescentDirectionNewtonMethod();
        descend.setFunction(f);
        DoubleVector ans = descend.findMin(new DoubleVector(-2d, -2d));
        List<Iteration> list = descend.getTable();

        for (Iteration it : list) {
            System.out.println(it.getX0().get(0) + " " + it.getX0().get(1));
        }
    }
}
