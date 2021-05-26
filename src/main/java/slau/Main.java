package slau;

import slau.matrix.LU;
import slau.matrix.LUMatrix;
import slau.matrix.Matrix;
import slau.methods.GaussMethod;
import slau.methods.LUMethod;
import slau.utils.TableGenerator;

public class Main {
    public static void main(final String[] args){
//        TableGenerator.generateTable("table3-3-5", new GaussMethod());
        TableGenerator.generateTable("test6", new GaussMethod(), new TableGenerator.Setting(10, 3, 20));
//        TableGenerator.generateTable(new GaussMethod(), new TableGenerator.Setting(5, 4, 20));
//        TableGenerator.generateTable(new LUMethod(), new TableGenerator.Setting(10, 3, 20));
//        TableGenerator.generateTable(new LUMethod(), new TableGenerator.Setting(5, 4, 20));

//        TableGenerator.generateTable("tableLU3-3-5", new LUMethod());
//        TableGenerator.generateTable("tableLU10-3-10", new LUMethod(), new TableGenerator.Setting(10, 3, 10));
//        TableGenerator.generateTableHilbert("hilbertGauss", new GaussMethod());
//        TableGenerator.generateTableHilbert("hilbertLU", new LUMethod());
    }
}
