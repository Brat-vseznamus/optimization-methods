package slau;

import slau.matrix.LU;
import slau.matrix.LUMatrix;
import slau.methods.GaussMethod;
import slau.utils.TableGenerator;

public class Main {
    public static void main(final String[] args){
        TableGenerator.generateTable("table3-3-5", new GaussMethod());
        TableGenerator.generateTable("table10-3-10", new GaussMethod(), new TableGenerator.Setting(10, 3, 10));
    }
}
