package days.day12;

public record CaveConnection(String cave1, String cave2) {
    static CaveConnection of(String caveConnectionStr) {
        String[] caves = caveConnectionStr.split("-");
        return new CaveConnection(caves[0], caves[1]);
    }
}
