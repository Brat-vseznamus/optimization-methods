package newton.quasi;

import expression.Expression;
import linear.DoubleMatrix;
import linear.DoubleVector;
import linear.Matrices;
import methods.dimensional.one.GoldenRatioMethod;
import newton.utils.FunctionExpression;
import newton.utils.Iteration;
import slau.methods.GaussWithMainElementMethod;

public class BFSQuasiNewtonMethod extends AbstractQuasiNewtonMethod {

    public BFSQuasiNewtonMethod(FunctionExpression function) {
        super(function);
    }

    public BFSQuasiNewtonMethod(Expression function, int n) {
        super(function, n);
    }

    public BFSQuasiNewtonMethod(FunctionExpression function, double eps) {
        super(function, eps);
    }

    public BFSQuasiNewtonMethod() {
    }

    @Override
    protected DoubleVector iteration(DoubleVector x0) {

        DoubleVector dw = function.gradient(x0).multiplyBy(-1).subtract(w);
        w = w.add(dw);
        DoubleVector v = g.multiply(dw);
        double ro = w.scalar(g.multiply(w));
        DoubleVector r = v.multiplyBy(1/ro).subtract(dx.multiplyBy(1/dx.scalar(dw)));
        g = (DoubleMatrix) g.subtract(dx.multiply(dx).multilpy(1d/dw.scalar(dx)));
        g = (DoubleMatrix) g.subtract(v.multiply(v).multilpy(1/ro)).add(r.multiply(r).multilpy(ro));
        p = g.multiply(w);
        double alpha = new GoldenRatioMethod(a -> {
            return function.evaluate(x0.add(p.multiplyBy(a)).toArray());
        }).findMin(0, 1);
        DoubleVector x1 = x0.add(p.multiplyBy(alpha));
        dx = x1.subtract(x0);
        return x1;
    }

    @Override
    public DoubleVector findMin(DoubleVector x0) {
        table.clear();
        int cnt = 100*n;
        // step 1
        g = Matrices.E(n);
        DoubleVector gx = function.gradient(x0);
        DoubleVector d = gx.multiplyBy(-1);
        // step 1.1
        double r;
        DoubleVector s;
        do {
            // step 2
            final DoubleVector finalx0 = x0;
            final DoubleVector finalD = d;
            r = new GoldenRatioMethod(a -> {
                return function.evaluate(finalx0.add(finalD.multiplyBy(a)).toArray());
            }).findMin(0, 1);
            System.out.println(r);
            s = d.multiplyBy(r);
            // step 3
            x0 = x0.add(s);
            DoubleVector gy = gx;
            // step 4
            gx = function.gradient(x0);
            p = gx.subtract(gy);
            DoubleVector u = s.subtract(g.multiply(p));
            // step 5
            g = (DoubleMatrix) g.add(p.multiply(p).multilpy(1d/s.scalar(p)));
            g = (DoubleMatrix) g.subtract(u.multiply(u).multilpy(1d/s.scalar(u)));

            // step 6
            double[][] cgdata = new double[g.getN()][g.getN()];
            for (int i = 0; i < cgdata.length; i++) {
                for (int j = 0; j < cgdata.length; j++) {
                    cgdata[i][j] = g.get(i, j);
                }
            }
            DoubleMatrix cg = new DoubleMatrix(cgdata);
            System.out.println(cg);
            System.out.println(gx.multiplyBy(-1));
            d = new DoubleVector(new GaussWithMainElementMethod().solve(cg, gx.multiplyBy(-1d).toArray()));
            // step 6.1 adding data
            table.add(new Iteration(
                    x0,
                    function.evaluate(x0.toArray()),
                    x0.add(s),
                    function.evaluate(x0.add(s).toArray()),
                    function.gradient(x0)));
        } while (s.norm() > eps && cnt-- > 0);

        if (cnt == 0) {
            return null;
        }
        return x0;

//        g = Matrices.E(n);
//        w = function.gradient(x0).multiplyBy(-1);
//        DoubleVector s;
//        int cnt = 100*n;
//        do {
//            DoubleVector finalX = x0;
//            double alpha = new GoldenRatioMethod(a -> {
//                return function.evaluate(finalX.add(w.multiplyBy(a)).toArray());
//            }).findMin(0, 1); // r
//
//            s = w.multiplyBy(alpha); // cnt s
//            x0 = x0.add(s);
//            p = w; // 3
//
//            w = function.gradient(x0);
//            dx = w.subtract(p);
//            DoubleVector u = s.subtract(g.multiply(dx)); // 4
//
//            g = (DoubleMatrix) g.add(u.multiply(u).multilpy(u.scalar(dx)));
//            w =
//            dx = x1.subtract(x0);
//            x0 = x1;
//            x1 = iteration(x0);
////            table.add(new Iteration(
////                    x0,
////                    function.evaluate(x0.toArray()),
////                    x1,
////                    function.evaluate(x1.toArray()),
////                    function.gradient(x0)));
//        } while (cnt-- > 0);
//        if (cnt == 0) {
//            return null;
//        }
//        return x1;
    }
}
