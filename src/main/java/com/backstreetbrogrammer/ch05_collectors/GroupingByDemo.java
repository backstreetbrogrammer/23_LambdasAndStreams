package com.backstreetbrogrammer.ch05_collectors;

import com.backstreetbrogrammer.model.StudentWithCourse;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GroupingByDemo {

    public static void main(String[] args) {
        final var john = new StudentWithCourse("John", 18, "Python");
        final var mary = new StudentWithCourse("Mary", 16, "Java");
        final var thomas = new StudentWithCourse("Thomas", 21, "Java");
        final var rahul = new StudentWithCourse("Rahul", 23, "JavaScript");
        final var jenny = new StudentWithCourse("Jenny", 17, "Python");
        final var tatiana = new StudentWithCourse("Tatiana", 25, "Java");

        final List<StudentWithCourse> students = List.of(john, mary, thomas, rahul, jenny, tatiana);

        final Map<String, List<StudentWithCourse>> studentsPerCourse =
                students.stream()
                        .collect(
                                Collectors.groupingBy(
                                        StudentWithCourse::getCourse
                                                     )
                                );

        studentsPerCourse.forEach((course, st) -> System.out.printf("Course: %s, Students Enrolled: %s%n",
                                                                    course,
                                                                    st));

        final Map<String, Long> countOfStudentsPerCourse =
                students.stream()
                        .collect(
                                Collectors.groupingBy(
                                        StudentWithCourse::getCourse, Collectors.counting()
                                                     )
                                );

        countOfStudentsPerCourse.forEach((course, cnt) -> System.out.printf("Course: %s, Number of Students Enrolled:" +
                                                                                    " %d%n",
                                                                            course,
                                                                            cnt));
    }

}
