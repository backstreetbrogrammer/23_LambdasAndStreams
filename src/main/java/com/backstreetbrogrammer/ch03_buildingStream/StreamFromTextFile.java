package com.backstreetbrogrammer.ch03_buildingStream;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class StreamFromTextFile {

    public static void main(final String[] args) {
        final Path path = Path.of("src", "main", "resources", "200words.txt");
        try (final Stream<String> lines = Files.lines(path)) {
            final long count = lines.count();
            System.out.printf("Count = %d%n", count);
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

}
