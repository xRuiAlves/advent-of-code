package days.day21;

import days.DaySolution;

import java.util.Map;

public class Day21 extends DaySolution {
    private static final int PART_1_TARGET_SCORE = 1000;
    private static final int PART_2_TARGET_SCORE = 21;
    private static final int CIRCLE_SIZE = 10;
    private static final int NUM_PLAYERS = 2;

    private static final Map<Integer, Integer> DIRAC_COMBINATIONS = Map.of(
            3, 1,
            4, 3,
            5, 6,
            6, 7,
            7, 6,
            8, 3,
            9, 1
    );
    private static final int DIRAC_MAX_INCREMENT = DIRAC_COMBINATIONS
        .keySet()
        .stream()
        .mapToInt(i -> i)
        .max()
        .getAsInt();

    public Day21(String filePath) {
        super(filePath);
    }

    @Override
    public Object part1() {
        int[] pos = startPos();
        int[] scores = new int[]{0, 0};
        int turn = 0;
        int dice = 1;

        while (scores[0] < PART_1_TARGET_SCORE && scores[1] < PART_1_TARGET_SCORE) {
            int idx = turn % NUM_PLAYERS;
            pos[idx] = nextPos(pos[idx], 3 * dice + 3);
            scores[idx] += pos[idx];
            dice += 3;
            ++turn;
        }

        return Math.min(scores[0], scores[1]) * (dice - 1);
    }

    private int nextPos(int currPos, int increment) {
        return ((currPos + increment - 1) % CIRCLE_SIZE) + 1;
    }

    @Override
    public Object part2() {
        int[] startPos = startPos();
        GameResult[][][][][] cache = new GameResult
                [NUM_PLAYERS]
                [CIRCLE_SIZE + 1]
                [CIRCLE_SIZE + 1]
                [PART_2_TARGET_SCORE + DIRAC_MAX_INCREMENT + 1]
                [PART_2_TARGET_SCORE + DIRAC_MAX_INCREMENT + 1];
        return visit(0, startPos[0], startPos[1], 0, 0, cache).winningPoints();
    }

    private int[] startPos() {
        return new int[]{
            parsePos(input.get(0)),
            parsePos(input.get(1))
        };
    }

    private int parsePos(String inputLine) {
        return inputLine.charAt(inputLine.length() - 1) - '0';
    }

    private GameResult visit(int turn, int pos1, int pos2, int score1, int score2, GameResult[][][][][] cache) {
        if (cache[turn][pos1][pos2][score1][score2] != null) {
            return cache[turn][pos1][pos2][score1][score2];
        }

        if (score1 >= 21) {
            return new GameResult(1, 0);
        }
        if (score2 >= 21) {
            return new GameResult(0, 1);
        }

        GameResult gameResult = new GameResult(0, 0);

        DIRAC_COMBINATIONS.forEach((dice, freq) -> {
            if (turn % 2 == 0) {
                int newPos = nextPos(pos1, dice);
                int newScore = score1 + newPos;
                GameResult nextResult = visit(1, newPos, pos2, newScore, score2, cache);
                gameResult.update(nextResult, freq);
            }
            else {
                int newPos = nextPos(pos2, dice);
                int newScore = score2 + newPos;
                GameResult nextResult = visit(0, pos1, newPos, score1, newScore, cache);
                gameResult.update(nextResult, freq);
            }
        });

        cache[turn][pos1][pos2][score1][score2] = gameResult;
        return gameResult;
    }
}
