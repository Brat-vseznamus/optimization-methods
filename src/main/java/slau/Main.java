package slau;

import slau.utils.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Main {
    public static void main(final String[] args){
        startTests();
    }

    public static void startTests() {
        for (final Method m : Main.class.getDeclaredMethods()) {
            if (m.isAnnotationPresent(Test.class)) {
                final Test annotation = m.getDeclaredAnnotation(Test.class);
                System.out.printf("Test[%s]%n", annotation.src());
                try {
                    m.invoke(null);
                } catch (final InvocationTargetException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
