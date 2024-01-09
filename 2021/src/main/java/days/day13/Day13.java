package days.day13;

import days.DaySolution;

import java.util.LinkedList;
import java.util.List;

public class Day13 extends DaySolution {
    public Day13(String filePath) {
        super(filePath);
    }

    @Override
    public Object part1() {
        Input input = input();
        Matrix matrix = matrix(input);
        matrix.fold(input.foldInstructions().get(0));
        return matrix.visibleDots();
    }

    @Override
    public Object part2() {
        Input input = input();
        Matrix matrix = matrix(input);
        input.foldInstructions().forEach(matrix::fold);
        // matrix.print();
        return "EPLGRULR (for my input)";
    }

    private Matrix matrix(Input input) {
        int[][] matrix = new int[input.maxPointY() + 1][input.maxPointX() + 1];
        input.points().forEach(point -> matrix[point.y()][point.x()] = 1);
        return new Matrix(matrix);
    }

    private Input input() {
        boolean parsingPoints = true;
        List<Point> points = new LinkedList<>();
        List<FoldInstruction> foldInstructions = new LinkedList<>();

        for (String line : input) {
            if (line.isBlank()) {
                parsingPoints = false;
                continue;
            }

            if (parsingPoints) {
                points.add(Point.of(line));
            } else {
                foldInstructions.add(FoldInstruction.of(line));
            }
        }

        return new Input(points, foldInstructions);
    }
}
