package days.day19;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Scanner {
    private final int id;
    private List<int[]> points;
    private Point position;

    Scanner(int id) {
        this.id = id;
        this.points = new LinkedList<>();
    }

    public void update(AxisDiff xAxisDiff, AxisDiff yAxisDiff, AxisDiff zAxisDiff) {
        points = points.stream().map(point ->
            new int[]{
                point[xAxisDiff.axisIndex()] * xAxisDiff.sign() - xAxisDiff.shift(),
                point[yAxisDiff.axisIndex()] * yAxisDiff.sign() - yAxisDiff.shift(),
                point[zAxisDiff.axisIndex()] * zAxisDiff.sign() - zAxisDiff.shift()
            }
        ).collect(Collectors.toList());
    }

    int id() {
        return id;
    }

    List<int[]> points() {
        return points;
    }

    void setPosition(Point position) {
        this.position = position;
    }

    static int manhattanDistance(Scanner s1, Scanner s2) {
        return Math.abs(s2.position.x() - s1.position.x())
            + Math.abs(s2.position.y() - s1.position.y())
            + Math.abs(s2.position.z() - s1.position.z());
    }
}
