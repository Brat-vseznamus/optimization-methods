package slau;

import slau.matrix.LU;
import slau.matrix.LUMatrix;

public class Main {
    public static void main(final String[] args){
        LU lu = new LUMatrix(
                new double[][]{
                        {3, 4, -9, 5},
                        {-15, -12, 50, -16},
                        {-27, -36, 73, 8},
                        {9, 12, -10, -16}
                }
        ).getLU();
        System.out.println(lu.getL());
        System.out.println(lu.getU());
    }
}
