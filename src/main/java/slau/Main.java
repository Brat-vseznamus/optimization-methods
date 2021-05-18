package slau;

import slau.matrix.Matrix;
import slau.matrix.RegularMatrix;
import slau.methods.GaussMethod;
import slau.utils.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;

public class Main {
    public static void main(String[] args){
        startTests();
    }

    public static void startTests() {
        for (Method m : Main.class.getDeclaredMethods()) {
            if (m.isAnnotationPresent(Test.class)) {
                Test annotation = m.getDeclaredAnnotation(Test.class);
                System.out.printf("Test[%s]%n", annotation.src());
                try {
                    m.invoke(null);
                } catch (InvocationTargetException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test(src = "gauss1")
    public static void test_01() {
        Matrix m = new RegularMatrix(
                new double[][]{
                        {1, 2, 3},
                        {2, 3, 4},
                        {3, 5, 6}
                });
        double[] numbers = new double[]{4, 5, 6};
        System.out.println(Arrays.toString(
                new GaussMethod().solve(m, numbers)));
    }

}
