package functions;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.UnaryOperator;

public class Main {
    public static void main(String[] args) {
        UnaryOperator<Double> function;
        function = x -> x * x + Math.exp(-0.35d * x);
        double left = -2d;
        double right = 3d;

        UnaryOperator<Double> multimodulfunction = x -> Math.sin(1 / x);

        double left2 = -1d;
        double right2 = 1d;

        // System.out.println(new DichotomyMethod(multimodulfunction).findMin(left2, right2));
        // System.out.println(new FibonacciMethod(multimodulfunction).findMin(left2, right2));
        // System.out.println(new GoldenRatioMethod(multimodulfunction).findMin(left2, right2));
        // System.out.println(new BrentsMethod(multimodulfunction).findMin(left2, right2));
        // System.out.println(new ParabolicMethod(multimodulfunction, 1e-9).findMin(left2, right2));


        OptimizationAlgorithm methodBrent = new BrentsMethod(function);
        // for (AbstractMethod.Pair<Double, Integer> pair : methodBrent.lnToCalculations(left, right)) {
        //     System.out.println(pair.b);
        // }

        ArrayList<AbstractMethod> methods = new ArrayList<>(List.of(
            new DichotomyMethod(function),
            new FibonacciMethod(function),
            new GoldenRatioMethod(function),
            new BrentsMethod(function),
            new ParabolicMethod(function)
        ));

        for (AbstractMethod method : methods) {
            System.out.println(method.getName());
            for (AbstractMethod.Pair<Double, Integer> pair : method.lnToCalculations(left, right)) {
                System.out.println(pair.b);
            }
        }

        methodBrent.findMin(left, right);
        List<AbstractMethod.Info> table = methodBrent.getTable();
        System.out.printf("%7s %7s %7s %7s %7s %7s%n", "Iteration", "left", "right", "X", "Y", "leng");
        for (int i = 0, tableSize = table.size(); i < tableSize; i++) {
            AbstractMethod.Info info = table.get(i);
            AbstractMethod.Info prev = null;
            if (i > 0) {
                prev = table.get(i - 1);
            }
            System.out.printf("%7d %7f %7f %10f %7f %7f%n", i, info.getLeft(), info.getRight(), info.getValue(), function.apply(info.getValue()), i == 0 ? 0 :
                    (Math.abs(prev.getLeft() - prev.getRight()) / Math.abs(info.getLeft() - info.getRight())));
        }
    }
}
