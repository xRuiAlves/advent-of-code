package days.day5;

public record Point(int x, int y) {
    boolean isInLineWith(Point other) {
        return x == other.x || y == other.y;
    }

    static Point of(String pointStr) {
        String[] tokens = pointStr.split(",");
        return new Point(
            Integer.parseInt(tokens[0]),
            Integer.parseInt(tokens[1])
        );
    }
}
