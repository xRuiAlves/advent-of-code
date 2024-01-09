package days.day13;

public record Point(int x, int y) {
    static Point of(String pointStr) {
        String[] tokens = pointStr.split(",");
        return new Point(
            Integer.parseInt(tokens[0]),
            Integer.parseInt(tokens[1])
        );
    }
}
