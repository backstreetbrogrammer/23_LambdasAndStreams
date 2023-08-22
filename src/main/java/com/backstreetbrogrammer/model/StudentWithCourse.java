package com.backstreetbrogrammer.model;

import java.util.Objects;

public class StudentWithCourse {

    private final String name;
    private final int age;
    private final String course;

    public StudentWithCourse(final String name, final int age, final String course) {
        this.name = name;
        this.age = age;
        this.course = course;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getCourse() {
        return course;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final StudentWithCourse that = (StudentWithCourse) o;
        return age == that.age && Objects.equals(name, that.name) && Objects.equals(course, that.course);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age, course);
    }
}
