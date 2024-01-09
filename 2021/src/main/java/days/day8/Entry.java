package days.day8;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public final class Entry {
    private final List<Set<Character>> uniqueSignalPatterns;
    private final List<Set<Character>> fourDigitOutputValue;

    Entry(String line) {
        String[] tokens = line.split(" \\| ");
        this.uniqueSignalPatterns = digitTokens(tokens[0]);
        this.fourDigitOutputValue = digitTokens(tokens[1]);
    }

    List<Set<Character>> uniqueSignalPatterns() {
        return uniqueSignalPatterns;
    }

    List<Set<Character>> fourDigitOutputValue() {
        return fourDigitOutputValue;
    }

    int getMessage() {
        Map<String, String> patternsToDigits = patternsToDigits();
        return Integer.parseInt(fourDigitOutputValue
                .stream()
                .map(Entry::charSetToSortedString)
                .map(patternsToDigits::get)
                .collect(Collectors.joining()));
    }

    private static List<Set<Character>> digitTokens(String token) {
        return Arrays
            .stream(token.split(" "))
                .map(string -> string
                    .chars()
                    .mapToObj(c -> (char) c)
                    .collect(Collectors.toSet()))
                .collect(Collectors.toList());
    }

    private Map<String, String> patternsToDigits() {
        Map<Integer, Set<Character>> digits = new HashMap<>();
        digits.put(1, find(isOne()));
        digits.put(7, find(isSeven()));
        digits.put(4, find(isFour()));
        digits.put(8, find(isEight()));
        digits.put(9, find(isNine(digits)));
        digits.put(6, find(isSix(digits)));
        digits.put(0, find(isZero(digits)));
        digits.put(3, find(isThree(digits)));
        digits.put(5, find(isFive(digits)));
        digits.put(2, find(isTwo(digits)));
        return digits
            .entrySet()
            .stream()
            .collect(Collectors.toMap(e -> charSetToSortedString(e.getValue()), e -> String.valueOf(e.getKey())));
    }

    private Predicate<Set<Character>> isZero(Map<Integer, Set<Character>> digits) {
        return element -> element.size() == 6 && element.containsAll(digits.get(1)) && !element.containsAll(digits.get(9));
    }

    private Predicate<Set<Character>> isOne() {
        return element -> element.size() == 2;
    }

    private Predicate<Set<Character>> isTwo(Map<Integer, Set<Character>> digits) {
        return element -> element.size() == 5 && !element.containsAll(digits.get(1)) && !element.containsAll(digits.get(5));
    }

    private Predicate<Set<Character>> isThree(Map<Integer, Set<Character>> digits) {
        return element -> element.size() == 5 && element.containsAll(digits.get(1));
    }

    private Predicate<Set<Character>> isFour() {
        return element -> element.size() == 4;
    }

    private Predicate<Set<Character>> isFive(Map<Integer, Set<Character>> digits) {
        return element -> element.size() == 5 && !element.containsAll(digits.get(1)) && digits.get(9).containsAll(element);
    }

    private Predicate<Set<Character>> isSix(Map<Integer, Set<Character>> digits) {
        return element -> element.size() == 6 && !element.containsAll(digits.get(1));
    }

    private Predicate<Set<Character>> isSeven() {
        return element -> element.size() == 3;
    }

    private Predicate<Set<Character>> isEight() {
        return element -> element.size() == 7;
    }

    private Predicate<Set<Character>> isNine(Map<Integer, Set<Character>> digits) {
        return element -> element.size() == 6 && element.containsAll(digits.get(4));
    }

    private Set<Character> find(Predicate<Set<Character>> predicate) {
        return uniqueSignalPatterns
                .stream()
                .filter(predicate)
                .findFirst()
                .get();
    }

    static private String charSetToSortedString(Set<Character> characters) {
        return characters
            .stream()
            .sorted()
            .map(String::valueOf)
            .collect(Collectors.joining());
    }
}
