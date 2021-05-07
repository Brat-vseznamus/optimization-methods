package methods.dimensional.poly;

import java.util.function.UnaryOperator;

import methods.Main;
import methods.dimensional.one.BrentsMethod;
import methods.dimensional.one.OneDimensionalOptimizationMethod;
import methods.dimensional.one.GoldenRatioMethod;

public class SteepestDescendMethod extends AbstractGradientMethod {
    final OneDimensionalOptimizationMethod method;

    public SteepestDescendMethod(final OneDimensionalOptimizationMethod method,
                                 final QuadraticForm form, final double eps) {
        super(form, eps);
        this.method = method;
    }

    public SteepestDescendMethod(final OneDimensionalOptimizationMethod method,
                                 final QuadraticForm form) {
        this(method, form, DEFAULT_EPS);
    }

    public SteepestDescendMethod(final QuadraticForm form) {
        // this(new BrentsMethod(), form, DEFAULT_EPS);
        this(new GoldenRatioMethod(), form, DEFAULT_EPS);
    }

    public SteepestDescendMethod(final OneDimensionalOptimizationMethod method) {
        this(method, null);
    }

    public SteepestDescendMethod() {
        this(null, null);
    }

    @Override
    public double[] findMin() {
        final int n = form.getN();
        // step 1
        DoubleVector x = new DoubleVector(n);
        double f_x = form.apply(x);
        table.add(new State(x, f_x));

        while (true) {
            // step 2
            long startIt = System.currentTimeMillis();
            final DoubleVector gradient = form.gradient(x);
            final double norm = gradient.norm();
            if (norm <= eps) {
                break;
            }
            // step 3
            final double a = 0d;
            final double b = 2d / form.getMaxValue();
            final DoubleVector xc = x;
            final UnaryOperator<Double> function = (arg) -> {
                DoubleVector vc = xc;
                vc = vc.subtract(gradient.multiplyBy(arg));
                return form.apply(vc);
            };
            method.setFunction(function);
            final double alpha = method.findMin(a, b);
            if (alpha <= eps / 10) {
                break;
            }
            final DoubleVector alphaX = gradient.multiplyBy(alpha);
            x = x.subtract(alphaX);
            f_x = form.apply(x);
            table.add(new State(x, f_x));
            long endIt = System.currentTimeMillis();
            String nextLine = "";
            System.out.print("\b".repeat(70));
            if ((endIt - startIt) / 1000d >= 0.5d) { 
                nextLine = "%n";
            }
            System.out.printf("|ans - x| = %.9f, time[%d] = %fs, norm() = %.8f" + nextLine, 
                Main.answer.subtract(x).norm(), 
                table.size(), 
                (endIt - startIt) / 1000d,
                norm);
        }
        System.out.print("\b".repeat(70));
        return x.stream().mapToDouble(v -> v).toArray();
    }

    // private double iteration(DoubleVector x, DoubleVector f_x, DoubleVector gradient) {
    //     final double a = 0d;
    //     final double b = 2d / form.getMaxValue();
    //     final DoubleVector xc = x;
    //     final UnaryOperator<Double> function = (arg) -> {
    //         DoubleVector vc = xc;
    //         vc = vc.subtract(gradient.multiplyBy(arg));
    //         return form.apply(vc);
    //     };
    //     method.setFunction(function);
    //     final double alpha = method.findMin(a, b);
    //     if (alpha <= eps / 10) {
    //         break;
    //     }
    //     return alpha;
    // }

    @Override
    public String getName() {
        return "Метод наискорейшего спуска";
    }
}
