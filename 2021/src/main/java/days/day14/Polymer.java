package days.day14;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public record Polymer(Map<String, Long> polymer, char lastLetter) {
    static Polymer of(String rawPolymer) {
        Map<String, Long> polymer = new HashMap<>();

        for (int i = 0; i < rawPolymer.length() - 1; ++i) {
            String pair = rawPolymer.substring(i, i + 2);
            polymer.put(pair, polymer.getOrDefault(pair, 0L) + 1);
        }

        return new Polymer(polymer, rawPolymer.charAt(rawPolymer.length() - 1));
    }

    Polymer getNextPolymer(Map<String, String> insertionRules) {
        Map<String, Long> nextPolymer = new HashMap<>();

        polymer.forEach((pair, count) -> {
            if (insertionRules.containsKey(pair)) {
                String toInsert = insertionRules.get(pair);
                String pair1 = pair.charAt(0) + toInsert;
                String pair2 = toInsert + pair.charAt(1);
                nextPolymer.put(pair1, nextPolymer.getOrDefault(pair1, 0L) + count);
                nextPolymer.put(pair2, nextPolymer.getOrDefault(pair2, 0L) + count);
            } else {
                nextPolymer.put(pair, nextPolymer.getOrDefault(pair, 0L) + count);
            }
        });

        return new Polymer(nextPolymer, lastLetter);
    }

    long countDiff() {
        Map<Character, Long> charCounts = new HashMap<>();
        polymer.forEach((pair, count) -> {
            char c = pair.charAt(0);
            charCounts.put(c, charCounts.getOrDefault(c, 0L) + count);
        });
        charCounts.put(lastLetter, charCounts.getOrDefault(lastLetter, 0L) + 1L);

        long max = charCounts.values().stream().filter(Predicate.not(Predicate.isEqual(0L))).mapToLong(l -> l).max().getAsLong();
        long min = charCounts.values().stream().filter(Predicate.not(Predicate.isEqual(0L))).mapToLong(l -> l).min().getAsLong();

        return max - min;
    }
}
