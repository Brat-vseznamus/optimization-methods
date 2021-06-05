package graphics;

import methods.dimensional.one.OneDimensionalOptimizationMethod;
import newton.ClassicNewtonMethod;
import newton.DescentDirectionNewtonMethod;
import newton.NewtonMethod;
import newton.OneDimOptimizedNewtonMethod;
import newton.quasi.BFSQuasiNewtonMethod;
import newton.quasi.PaulleQuasiNewtonMethod;
import newton.utils.Iteration;
import newton.utils.PythonUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import static graphics.controllers.ControllerLab4.functions;
import static graphics.controllers.ControllerLab4.startVectors;

public class PythonGenerator {

    public static Map<String, NewtonMethod> newtonMethods = Map.of(
            "classic", new ClassicNewtonMethod(),
            "oneDim", new OneDimOptimizedNewtonMethod(),
            "descent", new DescentDirectionNewtonMethod(),
            "bfs", new BFSQuasiNewtonMethod(),
            "paulle", new PaulleQuasiNewtonMethod()
    );

    public static void generateTxt(final int n, final String methodName) {
        final var func = functions[n];
        final var start = startVectors.get(n);
        final var method = newtonMethods.get(methodName);

        method.setFunction(func);
        method.findMin(start);

        List<Double> alphas = List.of();
        if (method instanceof DescentDirectionNewtonMethod) {
            alphas = ((DescentDirectionNewtonMethod) method).getAlphas();
        } else if (method instanceof OneDimOptimizedNewtonMethod) {
            alphas = ((OneDimOptimizedNewtonMethod) method).getAlphas();
        }

        PythonUtils.writeTwoDimIterations(
                method.getTable(),
                methodName + "_" + n + "_iter.txt",
                func.toPythonStyleString(),
                alphas);
        System.err.println("Printing function: " +
                func.toPythonStyleString() +
                "\n With method: " + methodName);
    }

    public static void main(final String[] args) {
        generateAll();
    }

    private static void generateAll() {
        IntStream.range(0, functions.length).forEach(i -> {
            for (final String methodName : newtonMethods.keySet()) {
                if (functions[i].getN() == 2) {
                    generateTxt(i, methodName);
                }
            }
        });
    }
}
