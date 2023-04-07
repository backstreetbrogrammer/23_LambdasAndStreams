package com.backstreetbrogrammer.ch02_mapFilterReduce;

import java.util.List;
import java.util.stream.Collectors;

public class FlatMapInterviewProblem {

    public static void main(final String[] args) {
        final var students = List.of("John", "Mary", "Peter");
        final var favoriteLanguages = List.of("Java", "Python");

        final var pairs
                = students.stream()
                          .flatMap(student -> favoriteLanguages.stream()
                                                               .map(favoriteLanguage ->
                                                                            new String[]{student, favoriteLanguage}))
                          .collect(Collectors.toList());

        pairs.forEach(val -> System.out.printf("(%s,%s)%n", val[0], val[1]));
    }

}
