package days.day10;

import days.DaySolution;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Day10 extends DaySolution {
    private static final Map<Character, Integer> ILLEGAL_DELIMETER_POINTS = Map.of(
        ')', 3,
        ']', 57,
        '}', 1197,
        '>', 25137
    );

    private static final Map<Character, Character> DELIMETER_MATCHES = Map.of(
        '(', ')',
        '[', ']',
        '{', '}',
        '<', '>'
    );

    private static final Map<Character, Integer> AUTOCOMPLETE_DELIMETER_POINTS = Map.of(
            ')', 1,
            ']', 2,
            '}', 3,
            '>', 4
    );

    private static final int DELIMITER_AUTOCOMPLETE_MULTIPLIER = 5;

    public Day10(String filePath) {
        super(filePath);
    }

    @Override
    public Object part1() {
        return input
            .stream()
            .mapToInt(Day10::corruptedPointValue)
            .sum();
    }

    @Override
    public Object part2() {
        List<Long> autocompleteValues = input
            .stream()
            .filter(Predicate.not(Day10::isCorrupted))
            .map(Day10::autocompletePointValue)
            .sorted()
            .collect(Collectors.toList());
        return autocompleteValues.get(autocompleteValues.size() / 2);
    }

    private static boolean isDelimiterStart(char delimiter) {
        return DELIMETER_MATCHES.containsKey(delimiter);
    }

    private static int corruptedPointValue(String line) {
        Stack<Character> delimiters = new Stack<>();

        for (char delimiter : line.toCharArray()) {
            if (isDelimiterStart(delimiter)) {
                delimiters.push(delimiter);
                continue;
            }

            char topDelimiter = delimiters.pop();
            if (delimiter != DELIMETER_MATCHES.get(topDelimiter)) {
                return ILLEGAL_DELIMETER_POINTS.get(delimiter);
            }
        }

        return 0;
    }

    private static boolean isCorrupted(String line) {
        return corruptedPointValue(line) != 0;
    }

    private static long autocompletePointValue(String line) {
        Stack<Character> delimiters = new Stack<>();

        for (char delimiter : line.toCharArray()) {
            if (isDelimiterStart(delimiter)) {
                delimiters.push(delimiter);
            } else {
                delimiters.pop();
            }
        }

        Collections.reverse(delimiters);
        return delimiters
            .stream()
            .map(DELIMETER_MATCHES::get)
            .map(AUTOCOMPLETE_DELIMETER_POINTS::get)
            .mapToLong(l -> l)
            .reduce(0, (acc, curr) -> acc * DELIMITER_AUTOCOMPLETE_MULTIPLIER + curr);
    }
}
