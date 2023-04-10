package com.backstreetbrogrammer.ch03_buildingStream;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamSkippingAndLimiting {

    public static void main(final String[] args) {
        IntStream.range(0, 30)
                 .skip(10)
                 .limit(10)
                 .forEach(index -> System.out.printf("%d ", index));

        System.out.println("\n-------------------");

        final Path path = Path.of("src", "main", "resources", "200words.txt");
        try (final Stream<String> lines = Files.lines(path)) {
            lines.skip(20).limit(10).forEach(System.out::println);
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

}
