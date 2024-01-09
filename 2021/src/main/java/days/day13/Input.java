package days.day13;

import java.util.List;

public record Input(List<Point> points, List<FoldInstruction> foldInstructions) {
    int maxPointX() {
        return points.stream().mapToInt(Point::x).reduce(Math::max).getAsInt();
    }

    int maxPointY() {
        return points.stream().mapToInt(Point::y).reduce(Math::max).getAsInt();
    }
}
