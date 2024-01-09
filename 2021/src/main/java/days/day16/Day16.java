package days.day16;

import days.DaySolution;

import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day16 extends DaySolution {
    private static final int LITERAL_VALUE_TYPE_ID = 4;
    private static final int LITERAL_VALUE_DIGIT_SIZE = 5;
    private static final int TYPE_ID_0_OPERATOR_LENGTH = 15;
    private static final int TYPE_ID_1_OPERATOR_LENGTH = 11;

    private static final Map<Character, String> HEX_TO_BIN = Map.ofEntries(
        Map.entry('0', "0000"),
        Map.entry('1', "0001"),
        Map.entry('2', "0010"),
        Map.entry('3', "0011"),
        Map.entry('4', "0100"),
        Map.entry('5', "0101"),
        Map.entry('6', "0110"),
        Map.entry('7', "0111"),
        Map.entry('8', "1000"),
        Map.entry('9', "1001"),
        Map.entry('A', "1010"),
        Map.entry('B', "1011"),
        Map.entry('C', "1100"),
        Map.entry('D', "1101"),
        Map.entry('E', "1110"),
        Map.entry('F', "1111")
    );

    int versionSum = 0;

    public Day16(String filePath) {
        super(filePath);
    }

    @Override
    public Object part1() {
        parsePacket(binInput(), 0);
        return versionSum;
    }

    @Override
    public Object part2() {
        return parsePacket(binInput(), 0).value();
    }

    private PacketResult parsePacket(String binInput, int pointer) {
        versionSum += Integer.parseInt(binInput.substring(pointer, pointer + 3), 2);
        int typeId = Integer.parseInt(binInput.substring(pointer + 3, pointer + 6), 2);

        PacketResult res = ((typeId == LITERAL_VALUE_TYPE_ID)
            ? parseLiteralPacket(binInput, pointer + 6)
            : parseOperatorPacket(binInput, pointer + 6, typeId));
        return new PacketResult(res.value(), 6 + res.shift());
    }

    private PacketResult parseLiteralPacket(String binInput, int pointer) {
        StringBuilder sb = new StringBuilder();
        int shift = 0;

        while (pointer + shift < binInput.length()){
            sb.append(binInput, pointer + shift + 1, pointer + shift + LITERAL_VALUE_DIGIT_SIZE);

            shift += LITERAL_VALUE_DIGIT_SIZE;

            if (binInput.charAt(pointer + shift - LITERAL_VALUE_DIGIT_SIZE) == '0') {
                break;
            }
        }

        long literalDecimalValue = Long.parseLong(sb.toString(), 2);
        return new PacketResult(literalDecimalValue, shift);
    }

    private PacketResult parseOperatorPacket(String binInput, int pointer, int typeId) {
        char lengthTypeId = binInput.charAt(pointer);
        int shift = 1;
        int innerShift = 0;
        List<Long> subPacketValues = new LinkedList<>();

        if (lengthTypeId == '0') {
            int length = Integer.parseInt(binInput.substring(pointer + 1, pointer + 1 + TYPE_ID_0_OPERATOR_LENGTH), 2);
            shift += TYPE_ID_0_OPERATOR_LENGTH;

            while (innerShift < length) {
                PacketResult res = parsePacket(binInput, pointer + shift + innerShift);
                innerShift += res.shift();
                subPacketValues.add(res.value());
            }
        } else {
            int numInnerPackets = Integer.parseInt(binInput.substring(pointer + 1, pointer + 1 + TYPE_ID_1_OPERATOR_LENGTH), 2);
            shift += TYPE_ID_1_OPERATOR_LENGTH;

            for (int i = 0; i < numInnerPackets; ++i) {
                PacketResult res = parsePacket(binInput, pointer + shift + innerShift);
                innerShift += res.shift();
                subPacketValues.add(res.value());
            }
        }

        return new PacketResult(op(typeId, subPacketValues), shift + innerShift);
    }

    private long op(int typeId, List<Long> subPacketValues) {
        if (typeId == 0) {
            return subPacketValues.stream().mapToLong(l -> l).sum();
        }
        if (typeId == 1) {
            return subPacketValues.stream().mapToLong(l -> l).reduce(1, (acc, curr) -> acc * curr);
        }
        if (typeId == 2) {
            return subPacketValues.stream().mapToLong(l -> l).min().getAsLong();
        }
        if (typeId == 3) {
            return subPacketValues.stream().mapToLong(l -> l).max().getAsLong();
        }
        if (typeId == 5) {
            return (subPacketValues.get(0) > subPacketValues.get(1))
                ? 1
                : 0;
        }
        if (typeId == 6) {
            return (subPacketValues.get(0) < subPacketValues.get(1))
                ? 1
                : 0;
        }
        if (typeId == 7) {
            return (subPacketValues.get(0).equals(subPacketValues.get(1)))
                ? 1
                : 0;
        }
        throw new RuntimeException(String.format("Invalid typeId: %d", typeId));
    }

    private String binInput() {
        return firstInputLine()
            .chars()
            .mapToObj(c -> (char) c)
            .map(HEX_TO_BIN::get)
            .collect(Collectors.joining());
    }
}
