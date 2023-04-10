package com.backstreetbrogrammer.ch03_buildingStream;

import java.util.stream.Stream;

public class StreamTakeWhileDropWhile {

    public static void main(final String[] args) {
        Stream.of(4, 4, 4, 5, 6, 7, 8, 9, 10)
              .takeWhile(number -> (number / 4 == 1))
              .forEach(index -> System.out.printf("%d ", index));

        System.out.println("\n-------------------");

        Stream.of(4, 4, 4, 5, 6, 7, 8, 9, 10)
              .dropWhile(number -> (number / 4 == 1))
              .forEach(index -> System.out.printf("%d ", index));
    }

}
