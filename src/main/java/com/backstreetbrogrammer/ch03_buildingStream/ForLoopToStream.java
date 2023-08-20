package com.backstreetbrogrammer.ch03_buildingStream;

import com.backstreetbrogrammer.model.Student;

import java.util.List;

public class ForLoopToStream {

    public static void main(final String[] args) {
        final var john = new Student("John", 18);
        final var mary = new Student("Mary", 16);
        final var thomas = new Student("Thomas", 21);
        final var rahul = new Student("Rahul", 23);
        final var jenny = new Student("Jenny", 17);
        final var tatiana = new Student("Tatiana", 25);

        final List<Student> students = List.of(john, mary, thomas, rahul, jenny, tatiana);

        final double averageAgeUsingForLoop = getAverageAgeUsingForLoop(students);
        System.out.printf("Average age using For-Loop = %.2f%n", averageAgeUsingForLoop);

        final double averageAgeUsingStreams = getAverageAgeUsingStreams(students);
        System.out.printf("Average age using Streams = %.2f%n", averageAgeUsingStreams);
    }

    private static double getAverageAgeUsingForLoop(final List<Student> students) {
        double average = 0D;
        int sum = 0;
        int count = 0;
        for (final Student student : students) {
            if (student.getAge() > 20) {
                count++;
                sum += student.getAge();
            }
        }
        if (count > 0) {
            average = sum / count;
        }

        return average;
    }

    private static double getAverageAgeUsingStreams(final List<Student> students) {
        return students.stream()
                       .mapToInt(Student::getAge)
                       .filter(age -> age > 20)
                       .average()
                       .orElseThrow();
    }

}
