package days.day20;

import days.DaySolution;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class Day20 extends DaySolution {
    private static final int PART_1_NUM_ENHANCEMENTS = 2;
    private static final int PART_2_NUM_ENHANCEMENTS = 50;

    public Day20(String filePath) {
        super(filePath);
    }

    @Override
    public Object part1() {
        return countLitPixels(PART_1_NUM_ENHANCEMENTS);
    }

    @Override
    public Object part2() {
        return countLitPixels(PART_2_NUM_ENHANCEMENTS);
    }

    private int countLitPixels(int numEnhancements) {
        Input input = input(numEnhancements);
        int[][] matrix = input.asMatrix();

        for (int i = 0; i < numEnhancements; ++i) {
            updateMatrix(matrix, input, i);
        }

        return countLitPixels(matrix);
    }

    private void updateMatrix(int[][] matrix, Input input, int iteration) {
        Queue<Integer> updatedNums = new LinkedList<>();

        for (int i = 0; i < matrix.length ; ++i) {
            for (int j = 0; j < matrix[i].length; ++j) {
                int index = getIndex(matrix, iteration, i, j);
                updatedNums.offer(input.enhancementAlgorithm().charAt(index) == '#' ? 1 : 0);
            }
        }

        for (int i = 0; i < matrix.length ; ++i) {
            for (int j = 0; j < matrix[i].length; ++j) {
                matrix[i][j] = updatedNums.poll();
            }
        }
    }

    private int getIndex(int[][] matrix, int iteration, int i, int j) {
        int index = 0;

        for (int y = i - 1; y <= i + 1; ++y) {
            for (int x = j - 1; x <= j + 1; ++x) {
                index = index * 2 + getValue(matrix, y, x, iteration);
            }
        }

        return index;
    }

    private int countLitPixels(int[][] matrix) {
        return Arrays
            .stream(matrix)
            .mapToInt(line -> Arrays.stream(line).sum())
            .sum();
    }

    private int getValue(int[][] matrix, int i, int j, int iteration) {
        int defaultValue = iteration % 2;
        return inBounds(matrix, i, j)
            ? matrix[i][j]
            : defaultValue;
    }

    private boolean inBounds(int[][] matrix, int i, int j) {
        return i >= 0 && j >= 0 && i < matrix.length && j < matrix[i].length;
    }

    private Input input(int numEnhancements) {
        return new Input(
            input.get(0),
            input.subList(2, input.size()),
            numEnhancements
        );
    }
}
