package days.day20;

import java.util.List;

public record Input(String enhancementAlgorithm, List<String> image, int numEnhancements) {
    int[][] asMatrix() {
        int[][] matrix = new int[image.size() + numEnhancements * 2][image.get(0).length() + numEnhancements * 2];

        for (int i = numEnhancements; i < numEnhancements + image.size(); ++i) {
            for (int j = numEnhancements; j < numEnhancements + image.get(0).length(); ++j) {
                matrix[i][j] = image.get(i - numEnhancements).charAt(j - numEnhancements) == '#' ? 1 : 0;
            }
        }

        return matrix;
    }
}
