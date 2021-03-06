import linear.DoubleVector;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import linear.Matrix;
import linear.SymmetricSparseMatrix;

import java.util.stream.IntStream;

public class SparseMatrixTest {

    @Test
    public void sparseMatrixGet() {
        final double[][] data = {
                {1, 2, 0, 2, 0},
                {2, 4, 6, 0, 5},
                {0, 6, 5, 0, 6},
                {2, 0, 0, 9, 0},
                {0, 5, 6, 0, 4}};
        final Matrix sparseMatrix = new SymmetricSparseMatrix(data);

        System.out.println(sparseMatrix.toString());

        final boolean result = IntStream.range(0, 5).allMatch(i ->
                IntStream.range(0, 5).allMatch(j -> {
                    final boolean eq = data[i][j] == sparseMatrix.get(i, j);
                    if (!eq) {
                        System.out.printf("data[%d][%d] = %f and matrix.get(%d, %d), = %f %n",
                                i, j, data[i][j], i, j, sparseMatrix.get(i, j));
                    }
                    return eq;
                })
        );


        Assertions.assertTrue(result);
    }

    @Test
    public void testFastMultiply() {
        final double[][] data = {
                {1, 2, 0, 2, 0},
                {2, 4, 6, 0, 5},
                {0, 6, 5, 0, 6},
                {2, 0, 0, 9, 0},
                {0, 5, 6, 0, 4}};
        final Matrix sparseMatrix = new SymmetricSparseMatrix(data);


        final DoubleVector vector = new DoubleVector(3, 2, 0, 6, 7);
        final DoubleVector answer = new DoubleVector(19, 49, 54, 60, 38);

        TestUtils.assertEqualsDoubleArrays(answer.toArray(), sparseMatrix.multiplyBy(vector).toArray());
    }
}
