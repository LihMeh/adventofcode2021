package day1;

import common.ResourceReaderKt;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Day1Test {

    @Test
    public void task1_example() {
        final var input = "199\n" +
                "200\n" +
                "208\n" +
                "210\n" +
                "200\n" +
                "207\n" +
                "240\n" +
                "269\n" +
                "260\n" +
                "263\n";
        assertThat(Day1.task1(input)).isEqualTo("7");
    }

    @Test
    public void task2_example() {
        final var input = "199\n" +
                "200\n" +
                "208\n" +
                "210\n" +
                "200\n" +
                "207\n" +
                "240\n" +
                "269\n" +
                "260\n" +
                "263\n";
        assertThat(Day1.task2(input)).isEqualTo("5");
    }

    @Test
    public void task1_real() {
        final var input = ResourceReaderKt.readResource("/day01_input.txt");
        assertThat(Day1.task1(input)).isEqualTo("1559");
    }

    @Test
    public void task2_real() {
        final var input = ResourceReaderKt.readResource("/day01_input.txt");
        assertThat(Day1.task2(input)).isEqualTo("1600");
    }

}