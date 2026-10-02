package com.example.domain;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;

/** Guards the inner ring: no file in domain may import Spring. */
class DomainHasNoSpringTest {

    @Test
    void domainSourcesDoNotImportSpring() throws IOException {
        Path domain = Path.of("src/main/java/com/example/domain");
        try (Stream<Path> files = Files.walk(domain)) {
            List<Path> offenders = files
                    .filter(p -> p.toString().endsWith(".java"))
                    .filter(DomainHasNoSpringTest::mentionsSpring)
                    .toList();
            assertTrue(offenders.isEmpty(), "Spring found in domain: " + offenders);
        }
    }

    private static boolean mentionsSpring(Path file) {
        try {
            return Files.readString(file).contains("org.springframework");
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
