package days.day21;

public class GameResult {
    private long p1;
    private long p2;

    GameResult(int p1, int p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    void update(GameResult nextResult, Integer freq) {
        p1 += nextResult.p1 * freq;
        p2 += nextResult.p2 * freq;
    }

    long winningPoints() {
        return Math.max(p1, p2);
    }
}
