package com.backstreetbrogrammer.ch02_mapFilterReduce;

import com.backstreetbrogrammer.model.Course;
import com.backstreetbrogrammer.model.Student;

import java.util.Arrays;
import java.util.List;

public class MapFilterReduceDemo {

    public static void main(final String[] args) {
        final var john = new Student("John", 18);
        final var mary = new Student("Mary", 16);
        final var thomas = new Student("Thomas", 21);
        final var rahul = new Student("Rahul", 23);
        final var jenny = new Student("Jenny", 17);
        final var tatiana = new Student("Tatiana", 25);

        final var students = Arrays.asList(john, mary, thomas, rahul, jenny, tatiana);

        final var countStudentsOlderThan20 = students.stream()
                                                     .mapToInt(student -> student.getAge())
                                                     .filter(age -> age >= 20)
                                                     .count();
        System.out.printf("Total no of students older than 20 years of age: %d%n", countStudentsOlderThan20);

        final var countStudentsLessThan20 = students.stream()
                                                    .mapToInt(student -> student.getAge())
                                                    .filter(age -> age < 20)
                                                    .count();
        System.out.printf("Total no of students less than 20 years of age: %d%n", countStudentsLessThan20);

        // Flat Map
        final var advancedJava = new Course("Advanced Java", john, mary);
        final var python = new Course("Python", thomas, rahul);
        final var algorithms = new Course("Algorithms", jenny, tatiana);

        final var courses = List.of(advancedJava, python, algorithms);
        courses.stream()
               .flatMap(course -> course.getStudents().stream())
               .map(p -> p.getName())
               .forEach(name -> System.out.println(name));
    }

}
