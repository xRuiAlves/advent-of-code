package days.day11;

import java.util.HashSet;
import java.util.Set;

public class Map {
    private static final int FLASH_LIMIT = 9;

    private final int[][] map;
    private final int size;

    Map(int[][] map) {
        this.map = map;
        this.size = map.length * map[0].length;
    }

    void flash() {
        for (int i = 0; i < map.length; ++i) {
            for (int j = 0; j < map[i].length; ++j) {
                ++map[i][j];
            }
        }

        Set<Integer> flashed = new HashSet<>();
        for (int i = 0; i < map.length; ++i) {
            for (int j = 0; j < map[i].length; ++j) {
                verifyFlash(i, j, flashed);
            }
        }
    }

    int resetAndCountFlashes() {
        int resetCount = 0;
        for (int i = 0; i < map.length; ++i) {
            for (int j = 0; j < map[i].length; ++j) {
                if (map[i][j] > FLASH_LIMIT) {
                    ++resetCount;
                    map[i][j] = 0;
                }
            }
        }
        return resetCount;
    }

    int size() {
        return size;
    }

    private void verifyFlash(int i, int j, Set<Integer> flashed) {
        propagateFlash(i, j, flashed, false);
    }

    private void propagateFlash(int i, int j, Set<Integer> flashed) {
        propagateFlash(i, j, flashed, true);
    }

    private void propagateFlash(int i, int j, Set<Integer> flashed, boolean fromPropagation) {
        if (isOutOfBounds(i, j) || flashed(i, j, flashed)) {
            return;
        }

        if (fromPropagation) {
            ++map[i][j];
        }

        if (map[i][j] <= FLASH_LIMIT) {
            return;
        }

        addFlashed(i, j, flashed);
        propagateFlash(i + 1, j + 1, flashed);
        propagateFlash(i + 1, j, flashed);
        propagateFlash(i + 1, j - 1, flashed);
        propagateFlash(i, j + 1, flashed);
        propagateFlash(i, j - 1, flashed);
        propagateFlash(i - 1, j + 1, flashed);
        propagateFlash(i - 1, j, flashed);
        propagateFlash(i - 1, j - 1, flashed);
    }

    private boolean isOutOfBounds(int i, int j) {
        return i < 0 || j < 0 || i >= map.length || j >= map[i].length;
    }

    private void addFlashed(int i, int j, Set<Integer> flashed) {
        flashed.add(flashedKey(i, j));
    }

    private boolean flashed(int i, int j, Set<Integer> flashed) {
        return flashed.contains(flashedKey(i, j));
    }

    private int flashedKey(int i, int j) {
        return i * map[i].length + j;
    }
}
