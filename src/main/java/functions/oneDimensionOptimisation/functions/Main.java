package functions.oneDimensionOptimisation.functions;

import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;
import functions.Pair;

public class Main {
    public static void main(final String[] args) {
        final UnaryOperator<Double> function;
        function = x -> x * x + Math.exp(-0.35d * x);
        final double left = -2d;
        final double right = 3d;

        final UnaryOperator<Double> multimodulfunction = x -> Math.sin(1 / x);

        final double left2 = -1d;
        final double right2 = 1d;

        final OptimizationAlgorithm methodBrent = new BrentsMethod(function);

        final ArrayList<AbstractMethod> methods = new ArrayList<>(List.of(
            new DichotomyMethod(function),
            new FibonacciMethod(function),
            new GoldenRatioMethod(function),
            new BrentsMethod(function),
            new ParabolicMethod(function)
        ));

        for (final AbstractMethod method : methods) {
            System.out.println(method.getName());
            for (final Pair<Double, Integer> pair : method.lnToCalculations(left, right)) {
                System.out.println(pair.second);
            }
        }

        methodBrent.findMin(left, right);
        final List<AbstractMethod.Info> table = methodBrent.getTable();
        System.out.printf("%7s %7s %7s %7s %7s %7s%n", "Iteration", "left", "right", "X", "Y", "leng");
        for (int i = 0, tableSize = table.size(); i < tableSize; i++) {
            final AbstractMethod.Info info = table.get(i);
            AbstractMethod.Info prev = null;
            if (i > 0) {
                prev = table.get(i - 1);
            }
            System.out.printf("%7d %7f %7f %10f %7f %7f%n", i, info.getLeft(), info.getRight(), info.getValue(), function.apply(info.getValue()), i == 0 ? 0 :
                    (Math.abs(prev.getLeft() - prev.getRight()) / Math.abs(info.getLeft() - info.getRight())));
        }
    }
}
