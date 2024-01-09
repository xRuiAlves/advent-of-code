package days.day13;

import java.util.Arrays;

public record FoldInstruction(char axis, int coordinate) {
    static FoldInstruction of(String fullFoldInstruction) {
        String foldInstruction = Arrays
            .stream(fullFoldInstruction.split(" "))
            .reduce((_acc, curr) -> curr)
            .get();

        String[] tokens = foldInstruction.split("=");
        return new FoldInstruction(
            tokens[0].charAt(0),
            Integer.parseInt(tokens[1])
        );
    }
}
