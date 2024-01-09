package days.day3;

import days.DaySolution;

import java.util.List;
import java.util.stream.Collectors;

public class Day3 extends DaySolution {
    public Day3(String filePath) {
        super(filePath);
    }

    @Override
    public Object part1() {
        String epsilon = epsilon(input);
        String gamma = negate(epsilon);
        int epsilonValue = Integer.parseInt(epsilon, 2);
        int gammaValue = Integer.parseInt(gamma, 2);
        return epsilonValue * gammaValue;
    }

    @Override
    public Object part2() {
        int oxygenGeneratorRating = getOxygenGeneratorRating(input);
        int co2ScrubberRating = getCO2ScrubberRating(input);
        return oxygenGeneratorRating * co2ScrubberRating;
    }

    private String epsilon(List<String> values) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < input.get(0).length(); ++i) {
            int bitCount = getBitCountInPosition(values, i);
            sb.append(bitCount > 0 ? 1 : 0);
        }

        return sb.toString();
    }

    private String negate(String epsilon) {
        return epsilon
            .chars()
            .mapToObj(c -> (char) c)
            .map(c -> c == '1' ? "0" : "1")
            .collect(Collectors.joining());
    }

    private int getBitCountInPosition(List<String> values, int position) {
        int count = 0;
        for (String value : values) {
            count += value.charAt(position) == '1' ? 1 : -1;
        }
        return count;
    }

    private int getOxygenGeneratorRating(List<String> values) {
        return getRating(values, true);
    }

    private int getCO2ScrubberRating(List<String> values) {
        return getRating(values, false);
    }

    private int getRating(List<String> values, boolean isOxygen) {
        List<String> currentValues = values;

        while (currentValues.size() > 1) {
            for (int i = 0; i < values.get(0).length() && currentValues.size() > 1; ++i) {
                int bitCount = getBitCountInPosition(currentValues, i);

                int position = i;
                char bit = bitCount >= 0
                    ? (isOxygen ? '1' : '0')
                    : (isOxygen ? '0' : '1');

                currentValues = currentValues
                    .stream()
                    .filter(value -> value.charAt(position) == bit)
                    .collect(Collectors.toList());
            }
        }

        return Integer.parseInt(currentValues.get(0), 2);
    }
}
