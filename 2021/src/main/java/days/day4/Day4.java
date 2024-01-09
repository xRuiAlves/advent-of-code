package days.day4;

import days.DaySolution;

import java.util.*;
import java.util.stream.Collectors;

public class Day4 extends DaySolution {
    private static final int BOARD_SIZE = 5;

    public Day4(String filePath) {
        super(filePath);
    }

    @Override
    public Object part1() {
        List<Integer> drawnNumbers = getDrawnNumbers();
        Set<Board> boards = parseBoards();

        for (int number : drawnNumbers) {
            Optional<Board> winningBoard = boards.stream().filter(board -> board.playNumber(number)).findFirst();
            if (winningBoard.isPresent()) {
                return winningBoard.get().unmarkedNumbersSum() * number;
            }
        }

        throw new RuntimeException("No board won!");
    }

    @Override
    public Object part2() {
        List<Integer> drawnNumbers = getDrawnNumbers();
        Set<Board> boards = parseBoards();
        Optional<Board> finalBoard = Optional.empty();

        for (int number : drawnNumbers) {
            boards.removeIf(board -> board.playNumber(number));
            if (boards.size() == 1) {
                finalBoard = Optional.of(boards.iterator().next());
            }
            if (boards.isEmpty()) {
                return number * finalBoard.get().unmarkedNumbersSum();
            }
        }

        throw new RuntimeException("No board won!");
    }

    private List<Integer> getDrawnNumbers() {
        return Arrays
                .stream(firstInputLine().split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    private Set<Board> parseBoards() {
        Set<Board> boards = new HashSet<>();

        for (int i = 2; i < input.size(); i += (BOARD_SIZE + 1)) {
            Set<Set<Integer>> boardRowsAndColumns = new HashSet<>();
            List<Set<Integer>> boardColumns = new ArrayList<>();
            Set<Integer> unmarkedNumbers = new HashSet<>();

            for (int col = 0; col < BOARD_SIZE; ++col) {
                boardColumns.add(new HashSet<>());
            }

            for (int row = 0; row < BOARD_SIZE; ++row) {
                List<Integer> boardRow = boardRow(input.get(i + row));
                unmarkedNumbers.addAll(boardRow);
                boardRowsAndColumns.add(new HashSet<>(boardRow));

                for (int col = 0; col < BOARD_SIZE; ++col) {
                    boardColumns.get(col).add(boardRow.get(col));
                }
            }

            boardRowsAndColumns.addAll(boardColumns);
            boards.add(new Board(boardRowsAndColumns, unmarkedNumbers));
        }

        return boards;
    }

    private List<Integer> boardRow(String row) {
        return Arrays
            .stream(row.trim().replace("  ", " ").split(" "))
            .map(Integer::valueOf)
            .collect(Collectors.toList());
    }

    private static record Board(Set<Set<Integer>> rowsAndColumns, Set<Integer> unmarkedNumbers) {
        boolean playNumber(int number) {
            for (Set<Integer> rowOrColumn : rowsAndColumns) {
                rowOrColumn.remove(number);
                unmarkedNumbers.remove(number);
                if (rowOrColumn.isEmpty()) {
                    return true;
                }
            }
            return false;
        }

        int unmarkedNumbersSum() {
            return unmarkedNumbers.stream().mapToInt(i -> i).sum();
        }
    }
}
