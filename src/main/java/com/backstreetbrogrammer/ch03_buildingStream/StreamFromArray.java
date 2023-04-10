package com.backstreetbrogrammer.ch03_buildingStream;

import com.backstreetbrogrammer.model.Student;

import java.util.Arrays;
import java.util.stream.Stream;

public class StreamFromArray {

    public static void main(final String[] args) {
        final var john = new Student("John", 18);
        final var mary = new Student("Mary", 16);
        final var thomas = new Student("Thomas", 21);
        final var rahul = new Student("Rahul", 23);
        final var jenny = new Student("Jenny", 17);
        final var tatiana = new Student("Tatiana", 25);

        final Student[] students = {john, mary, thomas, rahul, jenny, tatiana};

        // 1. Using Arrays.stream()
        Arrays.stream(students).forEach(System.out::println);

        System.out.println("-----------------------------");

        // 2. Using Stream.of()
        Stream.of(students).forEach(System.out::println);
    }

}
