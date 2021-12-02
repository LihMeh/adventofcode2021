package common;

import com.google.common.base.Charsets;

import java.io.IOException;

import static com.google.common.base.Preconditions.checkNotNull;

public class ResourceReader {

    public static String readResource(final String resourceName) {
        checkNotNull(resourceName, "resourceName required");

        try (var inputStream = ResourceReader.class.getResourceAsStream(resourceName)) {

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
