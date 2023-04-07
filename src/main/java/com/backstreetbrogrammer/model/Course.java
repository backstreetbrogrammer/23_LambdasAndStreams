package com.backstreetbrogrammer.model;

import java.util.ArrayList;
import java.util.List;

public class Course {

    private final String courseName;
    private final List<Student> students = new ArrayList<>();

    public Course(final String courseName, final Student... students) {
        this.courseName = courseName;
        this.students.addAll(List.of(students));
    }

    public String getCourseName() {
        return courseName;
    }

    public List<Student> getStudents() {
        return List.copyOf(students);
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseName='" + courseName + '\'' +
                ", students=" + students +
                '}';
    }
}
