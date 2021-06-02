package slau.methods;

import matrix.Matrix;

import java.util.stream.IntStream;

public class GaussWithMainElementMethod implements Method {

    int[] linesOrder;
    int[] columnOrder;

    @Override
    public double[] solve(final Matrix matrix, final double[] numbers) {
        final int rows = matrix.getN();
        if (rows != numbers.length) {
            throw new IllegalArgumentException("vector of numbers must have same size with matrix");
        }

        final int columns = matrix.getM();
        linesOrder = new int[rows];
        columnOrder = new int[columns];
        IntStream.range(0, Math.max(rows, columns)).forEach(index -> {
            if (index < rows) {
                linesOrder[index] = index;
            }
            if (index < columns) {
                columnOrder[index] = index;
            }
        });
        // Прямой ход
        for (int mainRowIndex = 0; mainRowIndex < rows - 1; mainRowIndex++) {
            // Выбор главного элемента, свап строчек и столбцов
            findAndSwapElement(mainRowIndex, matrix);
            final int mainRow = linesOrder[mainRowIndex];
            final int mainColumn = columnOrder[mainRowIndex];
            final double mainElement = matrix.get(mainRow, mainColumn);
            // Вычитание текущей строки из последующих
            for (int nextRowIndex = mainRowIndex + 1; nextRowIndex < rows; nextRowIndex++) {
                final int nextRow = linesOrder[nextRowIndex];
                final double aji = matrix.get(nextRow, mainColumn);
                for (int currentElementColumnIndex = mainRowIndex; currentElementColumnIndex < columns; currentElementColumnIndex++) {
                    final int currentElementColumn = columnOrder[currentElementColumnIndex];
                    matrix.set(nextRow, currentElementColumn,
                            matrix.get(nextRow, currentElementColumn)
                                    - aji / mainElement * matrix.get(mainRow, currentElementColumn));
                }
                numbers[nextRow] -= aji / mainElement * numbers[mainRow];
            }
        }

        // Обратный ход
        for (int mainRowIndex = rows - 1; mainRowIndex >= 0; mainRowIndex--) {
            final int mainRow = linesOrder[mainRowIndex];
            final int mainColumn = columnOrder[mainRowIndex];
            final double mainElement = matrix.get(mainRow, mainColumn);
            // Вычитание текущей строки из последующих
            for (int nextRowIndex = mainRowIndex - 1; nextRowIndex >= 0 && mainRowIndex > 0; nextRowIndex--) {
                final int nextRow = linesOrder[nextRowIndex];
                final double coefficient = matrix.get(nextRow, mainColumn) / mainElement;
                for (int currentElementColumnIndex = mainRowIndex; currentElementColumnIndex < columns; currentElementColumnIndex++) {
                    final int currentElementColumn = columnOrder[currentElementColumnIndex];
                    final double element = matrix.get(nextRow, currentElementColumn);
                    matrix.set(nextRow, currentElementColumn,
                            element - matrix.get(mainRow, currentElementColumn) * coefficient
                    );
                }
                numbers[nextRow] -= numbers[mainRow] * coefficient;
            }
            // Нормирование до единичной
            for (int currentElementColumnIndex = mainRowIndex; currentElementColumnIndex < columns; currentElementColumnIndex++) {
                final int currentElementColumn = columnOrder[currentElementColumnIndex];
                matrix.set(mainRow, currentElementColumn, matrix.get(mainRow, currentElementColumn) / mainElement);
            }
            numbers[mainRow] /= mainElement;
        }
        final double[] ans = new double[numbers.length];
        IntStream.range(0, rows).forEach(i -> ans[columnOrder[i]] = numbers[linesOrder[i]]);
        return ans;
    }

    private void findAndSwapElement(final int mainRowIndex, final Matrix matrix) {
        final int oldMainRow = linesOrder[mainRowIndex];
        final int oldMainColumn = columnOrder[mainRowIndex];

        final int rows = matrix.getN();
        final int columns = matrix.getM();

        int newMainRowIndex = mainRowIndex;
        int newMainColumnIndex = mainRowIndex;

        // Поиск главного элемента
        double maxElement = matrix.get(oldMainRow, oldMainColumn);
        for (int curRowIndex = mainRowIndex; curRowIndex < rows; curRowIndex++) {
            for (int curColumnIndex = mainRowIndex; curColumnIndex < columns; curColumnIndex++) {
                final int curRow = linesOrder[curRowIndex];
                final int curColumn = columnOrder[curColumnIndex];
                final double potentialMain = matrix.get(curRow, curColumn);
                if (Math.abs(potentialMain) > Math.abs(maxElement)) {
                    maxElement = potentialMain;
                    newMainRowIndex = curRowIndex;
                    newMainColumnIndex = curColumnIndex;
                }
            }
        }
        swap(newMainRowIndex, mainRowIndex, linesOrder);
        swap(newMainColumnIndex, mainRowIndex, columnOrder);
    }

    private void swap(final int i, final int j, final int[] array) {
        final int tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;
    }
}
