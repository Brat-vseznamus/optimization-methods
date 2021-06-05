package graphics;

import newton.ClassicNewtonMethod;
import newton.DescentDirectionNewtonMethod;
import newton.NewtonMethod;
import newton.OneDimOptimizedNewtonMethod;
import newton.utils.PythonUtils;

import java.util.Map;

import static graphics.controllers.ControllerLab4.functions;
import static graphics.controllers.ControllerLab4.startVectors;
import static graphics.controllers.ControllerLab4.x1;
import static graphics.controllers.ControllerLab4.x2;

public class PythonGenerator {

    public static Map<String, NewtonMethod> newtonMethods = Map.of(
            "classic", new ClassicNewtonMethod(),
            "oneDim", new OneDimOptimizedNewtonMethod(),
            "descent", new DescentDirectionNewtonMethod()
    );

    public static void generateTxt(final int n, final String methodName) {
        final var func = functions[n];
        final var start = startVectors.get(n);
        final var method = newtonMethods.get(methodName);

        method.setFunction(func);
        method.findMin(start);

        PythonUtils.printTwoDimensionalIterationsToFile(method.getTable(), methodName + "_iter.txt");
        System.err.println("Printing function: " +
                func.toString() +
                "\n With method: " + methodName);
    }

    public static void main(final String[] args) {
        generateTxt(0, "classic");
        generateTxt(0, "oneDim");
        generateTxt(0, "descent");
    }
}
