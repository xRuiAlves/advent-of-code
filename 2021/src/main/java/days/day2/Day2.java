package days.day2;

import days.DaySolution;

import java.util.List;
import java.util.stream.Collectors;

public class Day2 extends DaySolution {
    public Day2(String filePath) {
        super(filePath);
    }

    @Override
    public Object part1() {
        List<Move> moves = getMovesList();
        int depth = 0;
        int horizontalPosition = 0;

        for (Move move : moves) {
            switch (move.type) {
                case "forward":
                    horizontalPosition += move.amount;
                    break;
                case "up":
                    depth -= move.amount;
                    break;
                case "down":
                    depth += move.amount;
                    break;
                default:
                    throw new RuntimeException("Invalid move type " + move.type);
            }
        }

        return depth * horizontalPosition;
    }

    @Override
    public Object part2() {
        List<Move> moves = getMovesList();
        int depth = 0;
        int horizontalPosition = 0;
        int aim = 0;

        for (Move move : moves) {
            switch (move.type) {
                case "forward":
                    horizontalPosition += move.amount;
                    depth += move.amount * aim;
                    break;
                case "up":
                    aim -= move.amount;
                    break;
                case "down":
                    aim += move.amount;
                    break;
                default:
                    throw new RuntimeException("Invalid move type " + move.type);
            }
        }

        return depth * horizontalPosition;
    }

    private List<Move> getMovesList() {
        return input.stream().map(Day2::inputLineToRecord).collect(Collectors.toList());
    }

    private static Move inputLineToRecord(String line) {
        String[] tokens = line.split(" ");
        return new Move(tokens[0], Integer.parseInt(tokens[1]));
    }

    private record Move(String type, int amount) {}
}
