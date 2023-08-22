package com.backstreetbrogrammer.ch05_collectors;

import com.backstreetbrogrammer.model.Student;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CollectorsDemo {

    public static void main(final String[] args) {
        final var john = new Student("John", 18);
        final var mary = new Student("Mary", 16);
        final var thomas = new Student("Thomas", 21);
        final var rahul = new Student("Rahul", 23);
        final var jenny = new Student("Jenny", 17);
        final var tatiana = new Student("Tatiana", 25);

        final List<Student> students = List.of(john, mary, thomas, rahul, jenny, tatiana);

        // Collectors.toList()
        final List<String> collectorsToList
                = students.stream()
                          .map(Student::getName)
                          .collect(Collectors.toList());

        // Collectors.toUnmodifiableList()
        final List<String> collectorsToUnmodifiableList
                = students.stream()
                          .map(Student::getName)
                          .collect(Collectors.toUnmodifiableList());

        // Collectors.toSet()
        final Set<String> collectorsToSet
                = students.stream()
                          .map(Student::getName)
                          .collect(Collectors.toSet());

        // Collectors.toUnmodifiableSet()
        final Set<String> collectorsToUnmodifiableSet
                = students.stream()
                          .map(Student::getName)
                          .collect(Collectors.toUnmodifiableSet());

        // Collectors.toCollection()
        final List<String> collectorsToCollection
                = students.stream()
                          .map(Student::getName)
                          .collect(Collectors.toCollection(LinkedList::new));

        // Collectors.toMap()
        // Function.identity() is a function that accepts and returns the same value
        final Map<String, Integer> collectorsToMap
                = students.stream()
                          .map(Student::getName)
                          .collect(Collectors.toMap(Function.identity(), String::length));

        // Collectors.toUnmodifiableMap()
        final Map<String, Integer> collectorsToUnmodifiableMap
                = students.stream()
                          .map(Student::getName)
                          .collect(Collectors.toUnmodifiableMap(Function.identity(), String::length));

        // Collectors.collectingAndThen()
        final List<String> collectorsCollectingAndThen
                = students.stream()
                          .map(Student::getName)
                          .collect(Collectors.collectingAndThen(Collectors.toList(), Collections::unmodifiableList));

        // Collectors.joining()
        final String collectorsJoining
                = students.stream()
                          .map(Student::getName)
                          .collect(Collectors.joining(" "));

        // Collectors.counting()
        final Long collectorsCounting
                = students.stream()
                          .collect(Collectors.counting());

        // Collectors.summarizingDouble/Long/Int()
        final DoubleSummaryStatistics collectorsSummarizingDouble
                = students.stream()
                          .collect(Collectors.summarizingDouble(Student::getAge));
        /*
        collectorsSummarizingDouble.getAverage()
        collectorsSummarizingDouble.getCount()
        collectorsSummarizingDouble.getMax()
        collectorsSummarizingDouble.getMin()
        collectorsSummarizingDouble.getSum()
         */

        // Collectors.averagingDouble/Long/Int()
        final Double collectorsAveragingDouble
                = students.stream()
                          .collect(Collectors.averagingDouble(Student::getAge));

        // Collectors.summingDouble/Long/Int()
        final Double collectorsSummingDouble
                = students.stream()
                          .collect(Collectors.summingDouble(Student::getAge));

        // Collectors.maxBy()/minBy()
        final Optional<Integer> collectorsMaxBy
                = students.stream()
                          .map(Student::getAge)
                          .collect(Collectors.maxBy(Comparator.naturalOrder()));

        // Collectors.partitioningBy()
        final Map<Boolean, List<Student>> collectorsPartitioningBy
                = students.stream()
                          .collect(Collectors.partitioningBy(student -> student.getAge() > 20));
    }
}
