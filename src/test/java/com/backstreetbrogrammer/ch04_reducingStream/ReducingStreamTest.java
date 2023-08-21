package com.backstreetbrogrammer.ch04_reducingStream;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReducingStreamTest {

    @Test
    void testSumReduce() {
        final List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6);

        int result = numbers
                .stream()
                .reduce(0, Integer::sum);

        assertEquals(21, result);
    }

    @Test
    void testSumReduceParallel() {
        final List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6);

        int result = numbers
                .parallelStream()
                .reduce(0, Integer::sum, Integer::sum);

        assertEquals(21, result);
    }
}
