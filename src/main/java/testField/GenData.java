package testField;

import expression.*;
import linear.DoubleVector;
import newton.ClassicNewtonMethod;
import newton.DescentDirectionNewtonMethod;
import newton.NewtonMethod;
import newton.quasi.BFSQuasiNewtonMethod;
import newton.quasi.PaulleQuasiNewtonMethod;
import newton.utils.FunctionExpression;
import newton.utils.Iteration;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class GenData {
    public static X x1 = new X(0);
    public static X x2 = new X(1);
    public static X x3 = new X(2);
    public static X x4 = new X(3);

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

    public static void main(String[] args) {
        final NewtonMethod m = new ClassicNewtonMethod();
        final NewtonMethod m2 = new DescentDirectionNewtonMethod();

        int indf = 5;
        List<DoubleVector> starts = List.of(
                new DoubleVector(-2d, -2d),
                new DoubleVector(1d, 1d),
                new DoubleVector(4d, 0d),
                new DoubleVector(20d, 20d),
                new DoubleVector(0d, 0d));
        List<DoubleVector> starts2 = List.of(
                new DoubleVector(-2d, -2d, -2d, -2d),
                new DoubleVector(1d, 1d, 1d, 1d),
                new DoubleVector(4d, 0d, 5d, 0d));
//        genListOf(functions[indf - 1], m, 2 + ".(0, 0)", starts.get(4));
//        genListOf(functions[indf - 1], m2, 2 + ".(0, 0)", starts.get(4));
//        System.out.println(m.getTable().size() + " " + m2.getTable().size());

        for (int j = 0; j < starts.size(); j++) {
            DoubleVector start = starts.get(j);
            m.setFunction(functions[indf - 1]);
            m2.setFunction(functions[indf - 1]);
            m.findMin(start);
            m2.findMin(start);
            System.out.println("Sizes: " + m.getTable().size() + " " + m2.getTable().size());
            genListOf(functions[indf - 1], m, indf + "." + j, start);
            genListOf(functions[indf - 1], m2, indf + "." + j, start);
        }
    }

    public static Path genFile(NewtonMethod m, String name) {
        String fname = String.format("%s[%s]_iter.txt",
                m.getClass().getSimpleName().replaceAll("[a-z]", ""),
                name);
        Path p = Path.of("pythonUtils/iterations/", fname);
        try {
            return Files.createFile(p);
        } catch (FileAlreadyExistsException e) {
            return p;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void genListOf(FunctionExpression f, NewtonMethod m, int i) {
        genListOf(f, m, i, new DoubleVector(-2d, -2d));
    }

    public static void genListOf(FunctionExpression f, NewtonMethod m, int i, DoubleVector start) {
        genListOf(f, m, Integer.toString(i), start);
    }

    public static void genListOf(FunctionExpression f, NewtonMethod m, String addStr, DoubleVector start) {
        m.setFunction(f);
        DoubleVector ans = m.findMin(start);
        List<Iteration> list = m.getTable();
        Path p = genFile(m, "fun" + addStr);
        try {
            assert p != null;
            try (BufferedWriter bwr = Files.newBufferedWriter(p)) {
                System.out.println(f.toPythonStyleString());
                bwr.write(f.toPythonStyleString() + "\n");
                for (Iteration it : list) {
                    bwr.write(it.getX0().get(0) + " " + it.getX0().get(1) + "\n");
                }
            }
        } catch (IOException e) {
            System.out.println("Очень жаль");
        }
    }

    public static void genListOf(FunctionExpression f, NewtonMethod m, DoubleVector start) {
        m.setFunction(f);
        DoubleVector ans = m.findMin(start);
        List<Iteration> list = m.getTable();
        System.out.println(f.toPythonStyleString());
        for (Iteration it : list) {
            System.out.println(it.getX0());
        }
    }
}
