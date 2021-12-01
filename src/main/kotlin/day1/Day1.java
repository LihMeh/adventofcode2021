package day1;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

public class Day1 {

    public static List<Integer> parseIntList(final String input) {
        checkNotNull(input, "input required");
        return Stream.of(input.split("\n"))
                .map(Integer::parseUnsignedInt)
                .collect(Collectors.toUnmodifiableList());
    }

    public static String task1(final String input) {
        checkNotNull(input, "input required");

        final var depthReport = parseIntList(input);
        checkState(!depthReport.isEmpty(), "empty input");

        final var depthIter = depthReport.iterator();
        var current = depthIter.next();
        int increaseCount = 0;
        while (depthIter.hasNext()) {
            final var next = depthIter.next();
            if (next > current) {
                increaseCount++;
            }
            current = next;
        }

        return String.valueOf(increaseCount);
    }

    public static String task2(final String input) {
        checkNotNull(input, "input required");

        final var depthReport = parseIntList(input);
        checkState(depthReport.size() >= 3, "input is too short");

        int increaseCount = 0;
        for (int idx = 0; idx < depthReport.size() - 3; idx++) {
            final var common = depthReport.get(idx + 1) + depthReport.get(idx + 2);
            final var left = depthReport.get(idx) + common;
            final var right = depthReport.get(idx + 3) + common;
            if (right > left) {
                increaseCount++;
            }
        }

        return String.valueOf(increaseCount);
    }

}
