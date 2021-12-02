package day2;

import common.ResourceReader;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Day2Test {

    @Test
    public void task1_example() {
        assertThat(Day2.task1("forward 5\n" +
                "down 5\n" +
                "forward 8\n" +
                "up 3\n" +
                "down 8\n" +
                "forward 2"))
                .isEqualTo("150");
    }

    @Test
    public void task1_real() {
        final var input = ResourceReader.readResource("/day2_input.txt");
        assertThat(Day2.task1(input)).isEqualTo("1924923");
    }

    @Test
    public void task2_example() {
        assertThat(Day2.task2("forward 5\n" +
                "down 5\n" +
                "forward 8\n" +
                "up 3\n" +
                "down 8\n" +
                "forward 2"))
                .isEqualTo("900");
    }

    @Test
    public void task2_real() {
        final var input = ResourceReader.readResource("/day2_input.txt");
        assertThat(Day2.task2(input)).isEqualTo("1982495697");
    }

}
