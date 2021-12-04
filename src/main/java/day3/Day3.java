package day3;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;

public class Day3 {

    public static List<int[]> parseInput(final String input) {
        final var inputRows = input.split("\n");
        final var result = new ArrayList<int[]>(inputRows.length);

        final var rowSize = inputRows[0].length();
        for (final String inputRow : inputRows) {
            checkState(inputRow.length() == rowSize);
            final var parsedRow = inputRow.chars()
                    .map(chr -> chr == '0' ? 0 : 1)
                    .toArray();
            result.add(parsedRow);
        }

        return List.copyOf(result);
    }

    public static int[] calculateGammaRate(final List<int[]> diagnosticReport) {
        final var sum = summarize(diagnosticReport);
        final var gammaRateBin = new int[sum.length];
        final var threshold = diagnosticReport.size() / 2;
        for (int idx = 0; idx < gammaRateBin.length; idx++) {
            final var value = sum[idx] > threshold ? 1 : 0;
            gammaRateBin[idx] = value;
        }
        return gammaRateBin;
    }

    public static int[] summarize(final List<int[]> diagnosticReport) {
        return diagnosticReport.stream()
                .reduce((left, right) -> {
                    checkArgument(left.length == right.length);
                    final var result = new int[left.length];
                    for (int idx = 0; idx < left.length; idx++) {
                        result[idx] = left[idx] + right[idx];
                    }
                    return result;
                })
                .orElseThrow();
    }

    public static int[] negate(final int[] input) {
        final var result = new int[input.length];
        for (int idx = 0; idx < input.length; idx++) {
            final var inputBin = input[idx];
            checkState(inputBin == 1 || inputBin == 0);
            result[idx] = inputBin == 1 ? 0 : 1;
        }
        return result;
    }

    public static int toDecimal(final int[] input) {
        int result = 0;
        int currentPosValue = 1;
        for (int idx = input.length - 1; idx >= 0; idx--, currentPosValue *= 2) {
            final int currentBin = input[idx];
            checkState(currentBin == 0 || currentBin == 1);
            result += currentPosValue * currentBin;
        }
        return result;
    }

    public static int task2(final String input) {
        final var diagnostingReport = parseInput(input);

        final var oxyGenRating = extractMetric(diagnostingReport, (currentValue, positiveCount, negativeCount) -> {
            if (positiveCount == negativeCount) {
                return currentValue == 1;
            } else if (positiveCount > negativeCount) {
                return currentValue == 1;
            } else {
                return currentValue == 0;
            }
        });

        final var co2ScubRating = extractMetric(diagnostingReport, (currentValue, positiveCount, negativeCount) -> {
            if (positiveCount == negativeCount) {
                return currentValue == 0;
            } else if (negativeCount < positiveCount) {
                return currentValue == 0;
            } else {
                return currentValue == 1;
            }
        });

        return toDecimal(oxyGenRating) * toDecimal(co2ScubRating);
    }

    public interface Checker {
        boolean isPreserve(final int currentValue, final int positiveCount, final int totalCount);
    }

    private static int[] extractMetric(final List<int[]> diagnostingReport, final Checker predicate) {
        final var currentState = new ArrayList<>(diagnostingReport);
        int currentIdx = 0;
        while (currentState.size() > 1) {
            checkState(currentIdx < currentState.get(0).length);
            final var sum = summarize(currentState);
            final var positiveCount = sum[currentIdx];
            final var negativeCount = currentState.size() - positiveCount;

            final var iter = currentState.iterator();
            while (iter.hasNext()) {
                final var next = iter.next();
                final var isShouldPreserve = predicate.isPreserve(next[currentIdx], positiveCount, negativeCount);
                if (!isShouldPreserve) {
                    iter.remove();
                }
            }

            currentIdx++;
        }
        checkState(!currentState.isEmpty());
        System.out.println("Result: ");
        return currentState.get(0);
    }

}
