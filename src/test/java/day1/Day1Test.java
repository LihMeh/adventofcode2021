package day1;

import com.google.common.base.Charsets;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static com.google.common.base.Preconditions.checkNotNull;
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
        final var input = readResource("/day1_input.txt");
        assertThat(Day1.task1(input)).isEqualTo("1559");
    }

    @Test
    public void task2_real() {
        final var input = readResource("/day1_input.txt");
        assertThat(Day1.task2(input)).isEqualTo("1600");
    }

    public static String readResource(final String resourceName) {
        checkNotNull(resourceName, "resourceName required");

        try (var inputStream = Day1Test.class.getResourceAsStream(resourceName)) {

            if (inputStream == null) {
                throw new IllegalStateException("Resource not found " + inputStream);
            }

            final var resourceContentBytes = inputStream.readAllBytes();
            return new String(resourceContentBytes, Charsets.UTF_8);
        } catch (final IOException ex) {
            throw new RuntimeException("Failed to read resource " + resourceName, ex);
        }
    }

}