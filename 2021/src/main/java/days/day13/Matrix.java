package days.day13;

public class Matrix {
    final int[][] matrix;
    int height;
    int width;

    Matrix(int[][] matrix) {
        this.matrix = matrix;
        this.height = matrix.length;
        this.width = matrix[0].length;
    }

    void fold(FoldInstruction foldInstruction) {
        if (foldInstruction.axis() == 'y') {
            foldHorizontalLine(foldInstruction.coordinate());
        } else {
            foldVerticalLine(foldInstruction.coordinate());
        }
    }

    int visibleDots() {
        int count = 0;
        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                count += matrix[i][j];
            }
        }
        return count;
    }

    private void foldHorizontalLine(int coordinate) {
        for (int i = 0; i < width; ++i) {
            for (int j = 0; j < coordinate; ++j) {
                int overlap = Math.min(1, matrix[j][i] + matrix[height - j - 1][i]);
                matrix[j][i] = overlap;
            }
        }
        this.height = coordinate;
    }

    private void foldVerticalLine(int coordinate) {
        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < coordinate; ++j) {
                int overlap = Math.min(1, matrix[i][j] + matrix[i][width - j - 1]);
                matrix[i][j] = overlap;
            }
        }
        this.width = coordinate;
    }


    void print() {
        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
