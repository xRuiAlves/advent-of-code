package days.day5;

import java.util.LinkedList;
import java.util.List;

record Line(Point pi, Point pf) {
    boolean isStraightLine() {
        return pi.isInLineWith(pf);
    }

    List<Point> points() {
        if (!isStraightLine()) {
            return diagonalPoints();
        }
        return isHorizontal()
            ? horizontalPoints()
            : verticalPoints();
    }

    private List<Point> horizontalPoints() {
        int start = Math.min(pi.x(), pf.x());
        int end = Math.max(pi.x(), pf.x());
        List<Point> points = new LinkedList<>();

        for (int x = start; x <= end; ++x) {
            points.add(new Point(x, pi.y()));
        }

        return points;
    }

    private List<Point> verticalPoints() {
        int start = Math.min(pi.y(), pf.y());
        int end = Math.max(pi.y(), pf.y());
        List<Point> points = new LinkedList<>();

        for (int y = start; y <= end; ++y) {
            points.add(new Point(pi.x(), y));
        }

        return points;
    }

    private List<Point> diagonalPoints() {
        Point start = pi.x() < pf.x() ? pi : pf;
        Point end = start.equals(pi) ? pf : pi;
        List<Point> points = new LinkedList<>();
        int yIncrement = end.y() > start.y() ? 1 : -1;

        for (int x = start.x(), y = start.y(); x <= end.x(); x += 1, y += yIncrement) {
            points.add(new Point(x, y));
        }

        return points;
    }

    private boolean isHorizontal() {
        return pi.y() == pf.y();
    }

    static Line of(String lineStr) {
        String[] tokens = lineStr.split(" -> ");
        return new Line(
            Point.of(tokens[0]),
            Point.of(tokens[1])
        );
    }
}
