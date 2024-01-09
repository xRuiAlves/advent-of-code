package days.day19;

public record Point(int x, int y, int z) {
    static Point of(int[] coords) {
        return new Point(
            coords[0],
            coords[1],
            coords[2]
        );
    }
}
