package slau;

import slau.matrix.LU;
import slau.matrix.LUMatrix;
import slau.matrix.Matrix;
import slau.methods.GaussMethod;
import slau.methods.LUMethod;
import slau.utils.FormulaGenerator;
import slau.utils.TableGenerator;

public class Main {
    public static void main(final String[] args){
//        experimentWithZeroPercent();
//        gaussTests();
//        luTests();
        hilbert();
    }

    public static void experimentWithZeroPercent() {
        for (double percent = 3; percent <= 10; percent+=1) {
            double finalPercent = percent / 10d;
            TableGenerator.generateTable(
                    "zeros/test[zp=" + Double.toString(finalPercent).replace(".", ",") + "]",
                    new GaussMethod(),
                    new TableGenerator.Setting(10, 2, 20),
                    (u, v) -> FormulaGenerator.genRandomMatrix(u, v, finalPercent));
            System.out.printf("zp = %f dome%n", finalPercent);
        }
    }

    public static void gaussTests() {
        for (int dim : new int[]{3, 5, 10}) {
            TableGenerator.generateTable(
                    "gauss/test[dim = "+ dim +"]",
                    new GaussMethod(),
                    new TableGenerator.Setting(dim, 3, 20));
            System.out.printf("dim = %d done%n", dim);
        }
    }

    public static void luTests() {
        for (int dim : new int[]{3, 5, 10}) {
            TableGenerator.generateTable(
                    "lu/test[dim = "+ dim +"]",
                    new LUMethod(),
                    new TableGenerator.Setting(dim, 3, 20));
            System.out.printf("dim = %d done%n", dim);
        }
    }

    public static void hilbert() {
        int[] dims = new int[]{1, 2, 3, 5, 10, 15, 30, 50, 100, 200, 500, 1000};
        TableGenerator.generateTableHilbert(
                "hilbert/gauss_test",
                new GaussMethod(),
                dims);
//        TableGenerator.generateTableHilbert(
//                "hilbert/lu_test",
//                new LUMethod(),
//                dims);
    }

}
