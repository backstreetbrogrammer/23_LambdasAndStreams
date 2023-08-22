# Lambdas and Streams in Java

> This is a tutorials course covering lambdas and streams in Java.

Tools used:

- JDK 11
- Maven
- JUnit 5, Mockito
- IntelliJ IDE

## Table of contents

1. [Introduction to Lambdas](https://github.com/backstreetbrogrammer/23_LambdasAndStreams#chapter-01-introduction-to-lambdas)
    - [Lambda Expressions and Functional Interfaces](https://github.com/backstreetbrogrammer/23_LambdasAndStreams#lambda-expressions-and-functional-interfaces)
    - [Exploring `java.util.function` package](https://github.com/backstreetbrogrammer/23_LambdasAndStreams#exploring-javautilfunction-package)
    - [Lambdas vs Anonymous classes](https://github.com/backstreetbrogrammer/23_LambdasAndStreams#lambdas-vs-anonymous-classes)
    - [Chaining and Composing Lambdas](https://github.com/backstreetbrogrammer/23_LambdasAndStreams#chaining-and-composing-lambdas)
2. [Map Filter Reduce Algorithm](https://github.com/backstreetbrogrammer/23_LambdasAndStreams#chapter-02-map-filter-reduce-algorithm)
3. [Building a Stream](https://github.com/backstreetbrogrammer/23_LambdasAndStreams#chapter-03-building-a-stream)
    - [Creating a Stream from array](https://github.com/backstreetbrogrammer/23_LambdasAndStreams#creating-a-stream-from-array)
    - [Creating a Stream from Collection](https://github.com/backstreetbrogrammer/23_LambdasAndStreams#creating-a-stream-from-collection)
    - [Creating a Stream from Text File](https://github.com/backstreetbrogrammer/23_LambdasAndStreams#creating-a-stream-from-text-file)
    - [Creating a Stream using Regular Expression](https://github.com/backstreetbrogrammer/23_LambdasAndStreams#creating-a-stream-using-regular-expression)
    - [Creating a Stream from String](https://github.com/backstreetbrogrammer/23_LambdasAndStreams#creating-a-stream-from-string)
    - [Selecting elements of a Stream](https://github.com/backstreetbrogrammer/23_LambdasAndStreams#selecting-elements-of-a-stream)
    - [Converting a `for` loop to a Stream](https://github.com/backstreetbrogrammer/23_LambdasAndStreams#converting-a-for-loop-to-a-stream)
4. [Reducing Data using Stream](https://github.com/backstreetbrogrammer/23_LambdasAndStreams#chapter-04-reducing-data-using-stream)
5. [Collecting data from Stream](https://github.com/backstreetbrogrammer/23_LambdasAndStreams#chapter-05-collecting-data-from-stream)

---

### Chapter 01. Introduction to Lambdas

#### Lambda Expressions and Functional Interfaces

Lambda expressions basically express instances of **functional interfaces**.

What is a **Functional interface**?

An interface with **single** abstract method is called functional interface.

- `default` and `static` methods in interface do **NOT** count.
- methods from `java.lang.Object` do **NOT** count (`equals()`, `toString()`, `hashCode()`, etc.)

An example is `java.lang.Runnable`.

```java
package java.lang;

@FunctionalInterface
public interface Runnable {
    void run();
}
```

Lambda expressions implement the only abstract function and therefore implement functional interfaces.

Lambda expressions are added in Java 8 and provide below functionalities:

- Enable to treat functionality as a **method argument**, or code as **data**.
- A function that can be created without belonging to any class.
- A lambda expression can be passed around as if it was an object and executed on demand.

**Example:**

Implement a functional interface.

```java

@FunctionalInterface
public interface Supplier<T> {
    T get();
}
```

```
Supplier<String> supplier = () -> "Hello Students!!";
```

Complete source code:

```java
import java.util.function.Supplier;

public class LambdaDemo {

    public static void main(final String[] args) {
        final Supplier<String> supplier = () -> "Hello Students!!";
        System.out.println(supplier.get());
    }

}
```

Output:

```
Hello Students!!
```

Another example using **Consumer**:

```java

@FunctionalInterface
public interface Consumer<T> {
    void accept(T t);

    default Consumer<T> andThen(final Consumer<? super T> after) {
        Objects.requireNonNull(after);
        return (T t) -> {
            accept(t);
            after.accept(t);
        };
    }
}
```

Complete source code:

```java
import java.util.Locale;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class LambdaDemo {

    public static void main(final String[] args) {
        // Supplier
        final Supplier<String> supplier = () -> "Hello Students!!";
        System.out.println(supplier.get());

        // Consumer
        final Consumer<String> consumer = (String s) -> { // need to put in curly braces if more than 1 statements
            System.out.println(s.toUpperCase(Locale.ROOT));
            System.out.println(s.toLowerCase(Locale.ROOT));
        };
        consumer.accept(supplier.get());
    }

}
```

Output:

```
Hello Students!!
HELLO STUDENTS!!
hello students!!
```

![Lambda Syntax](LambdaSyntax.PNG)

#### Exploring `java.util.function` package

JDK has more than 40 interfaces in `java.util.function` package organized in 4 categories:

- Supplier
- Consumer
- Predicate
- Function

#### Supplier

The `Supplier`

- does not take any argument
- produces a value

```java
public interface Supplier<T> {
    T get();
}
```

```
Supplier<String> supplier = () -> "Hello";
```

#### Consumer

The `Consumer`

- takes any argument
- does not return anything

```java
public interface Consumer<T> {
    void accept(T t);
}
```

```
Consumer<String> consumer = s -> System.out.println(s);
```

#### Predicate

The `Predicate`

- takes any argument
- returns a **boolean**

Used to filter data.

```java
public interface Predicate<T> {
    boolean test(T t);
}
```

```
Predicate<String> isEmpty = s -> s.isEmpty();
```

#### Function

The `Function`

- takes any argument
- returns any type

Used to map data.

```java
public interface Function<T, R> {
    R apply(T t);
}
```

```
Function<Student, String> getStudentId = student -> student.getId();
```

#### Runnable

Although `Runnable` interface lies in `java.lang` package, it is still a functional interface. Thus, any interface which
has ONLY ONE abstract method is always a functional interface and annotating with `@FunctionalInterface` is optional.

The `Runnable`

- does not take any argument
- does not return anything

Used for defining thread task.

```java
public interface Runnable {
    void run();
}
```

```
Runnable runMe = () -> System.out.println("I am running in a separate thread");
```

#### Interview Problem 1 (Societe Generale): Demonstrate functional interfaces in code

Given a **Java POJO**:

```java
public class Student {

    private final String name;
    private final int age;

    public Student(final String name, final int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

}
```

1. Print all students using `Consumer`
2. Print all students names using `Function` and `Consumer`
3. Print all students names starting with **'T'** using `Predicate` and `Consumer`

**Solution**:

```java
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class FunctionalInterfacesDemo {

    public static void main(final String[] args) {
        final var john = new Student("John", 18);
        final var mary = new Student("Mary", 16);
        final var thomas = new Student("Thomas", 21);
        final var rahul = new Student("Rahul", 23);
        final var jenny = new Student("Jenny", 17);
        final var tatiana = new Student("Tatiana", 25);

        final var students = List.of(john, mary, thomas, rahul, jenny, tatiana);
        System.out.println("1. Print all students using Consumer~>");
        // Consumer
        students.forEach(student -> System.out.println(student));
        System.out.println("----------------------");

        final List<String> names = new ArrayList<>();

        // Function
        final Function<Student, String> toName = (Student student) -> student.getName();

        // Consumer
        students.forEach(student -> {
            final String name = toName.apply(student); // Function mapping
            names.add(name);
        });

        System.out.println("2. Print all students names using Function and Consumer~>");
        names.forEach(name -> System.out.println(name));
        System.out.println("----------------------");

        // Predicate
        final Predicate<String> startsWithT = name -> !name.startsWith("T");
        names.removeIf(startsWithT);
        // OR,
        // names.removeIf(name -> !name.startsWith("T")); // inline

        System.out.println("3. Print all students names starting with 'T' using Predicate and Consumer~>");
        // Consumer
        names.forEach(name -> System.out.println(name));
        System.out.println("----------------------");
    }

}
```

**Output**:

```
1. Print all students using Consumer~>
Student{name='John', age=18}
Student{name='Mary', age=16}
Student{name='Thomas', age=21}
Student{name='Rahul', age=23}
Student{name='Jenny', age=17}
Student{name='Tatiana', age=25}
----------------------
2. Print all students names using Function and Consumer~>
John
Mary
Thomas
Rahul
Jenny
Tatiana
----------------------
3. Print all students names starting with 'T' using Predicate and Consumer~>
Thomas
Tatiana
----------------------
```

#### Lambdas vs Anonymous classes

Prior to Java 8, the primary means of creating a **function object** was the **anonymous class**.

Code Snippet to sort a list of strings in order of length using an anonymous class to create the sort's comparison
function (which imposes the sort order):

```
        Collections.sort(words, new Comparator<String>() {
            @Override
            public int compare(final String s1, final String s2) {
                return Integer.compare(s1.length(), s2.length());
            }
        });
```

Lambdas are similar in function to anonymous class, but far more concise.

```
        Collections.sort(words, (s1, s2) -> Integer.compare(s1.length(), s2.length()));
        // OR
        Collections.sort(words, Comparator.comparingInt(String::length));
        // OR
        words.sort(Comparator.comparingInt(String::length));
```

The most important difference is in the **performance** => lambdas are more than **60 times faster** than anonymous
classes!

The reason is the way the Java compiler compiles the lambdas versus the anonymous classes.

Java compiler uses a special `invokedynamic` call to compile lambdas and thus the compiled code is different and much
faster.

Other reason is automatic **"boxing"** and **"unboxing"** of primitives and their wrapper classes.

For ex:

```
        Comparator<Integer> cmp = (i1, i2) -> Integer.compare(i1, i2);
        int compared = cmp.compare(5, 10);
```

Comparator interface:

```java
public interface Comparator<T> {
    int compare(T o1, T o2);
}
```

So above code will be interpreted as when we call compare method: `int compared = cmp.compare(5, 10);`

```java
public interface Comparator<Integer> {
    int compare(Integer o1, Integer o2);
}
```

Firstly, **boxing** will be done to promote primitive `int` 5 and 10 to `Integer`.

After the comparison, resulting `Integer` (a negative integer, zero, or a positive integer as the first argument is less
than, equal to, or greater than the second) will be converted back or **unboxed** to primitive `int`.

If this code is called on millions of integers => this will badly impact the performance.

Thus, to overcome this - a set of functional interfaces are added for primitive types which can be used by lambdas, for
ex:

- `IntPredicate`
- `LongSupplier`
- `IntFunction<T>`
- `LongToIntFunction`

Just taking an example again, in the `Supplier` interface, we have a generic type `T` returned via `get()` method.

```java
public interface Supplier<T> {
    T get();
}
```

However, for `LongSupplier` interface, we can directly get primitive `long` via `getAsLong()` method as return value
without any need to **box** it to wrapper `Long`.

```java
public interface LongSupplier {
    long getAsLong();
}
```

Similarly, for `DoubleToIntFunction` interface, we can directly work with primitives to create the function to
convert `double` to `int`.

```java
public interface DoubleToIntFunction {
    int applyAsInt(double value);
}
```

Complete Java code example to illustrate this:

```java
import java.util.function.DoubleToIntFunction;
import java.util.function.LongSupplier;

public class PrimitiveLambdas {

    public static void main(final String[] args) {
        final LongSupplier supplier = () -> 10L;
        final long i = supplier.getAsLong();
        System.out.printf("i = %d%n", i);

        final DoubleToIntFunction function = value -> (int) Math.ceil(value);
        final int pi = function.applyAsInt(Math.PI);
        System.out.printf("PI = %d%n", pi);
    }

}
```

**Output**:

```
i = 10
PI = 4
```

#### Chaining and Composing Lambdas

We can create new lambdas by combining existing lambdas:

- Predicate
- Consumer
- Function

We can also modify lambdas. All the above added functionalities are possible because of `default` or `static` methods
defined in the functional interfaces. And, there is still **only one** abstract method.

**Example source code**:

```java
import java.util.Locale;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class ChainingLambdas {

    public static void main(final String[] args) {
        // Predicate
        final Predicate<String> isNull = s -> s == null;
        System.out.println("Using 'isNull' Predicate~>");
        System.out.printf("For null = %b%n", isNull.test(null));
        System.out.printf("For 'Hello Students' = %b%n", isNull.test("Hello Students"));
        System.out.println("------------------------");

        final Predicate<String> isEmpty = s -> s.isEmpty();
        System.out.println("Using 'isEmpty' Predicate~>");
        System.out.printf("For empty = %b%n", isEmpty.test(""));
        System.out.printf("For 'Hello Students' = %b%n", isEmpty.test("Hello Students"));
        System.out.println("------------------------");

        final Predicate<String> isNotNullOrEmpty = isNull.negate().and(isEmpty.negate()); // combine
        System.out.println("Using 'isNotNullOrEmpty' Predicate~>");
        System.out.printf("For null = %b%n", isNotNullOrEmpty.test(null));
        System.out.printf("For empty = %b%n", isNotNullOrEmpty.test(""));
        System.out.printf("For 'Hello Students' = %b%n", isNotNullOrEmpty.test("Hello Students"));
        System.out.println("------------------------");

        // Consumer
        final Consumer<String> c1 = s -> System.out.printf("c1 consumer prints as upper case: %s%n",
                                                           s.toUpperCase(Locale.ROOT));
        final Consumer<String> c2 = s -> System.out.printf("c2 consumer prints as lower case: %s%n",
                                                           s.toLowerCase(Locale.ROOT));

        final Consumer<String> c3 = c1.andThen(c2); // combine
        System.out.println("Using 'andThen' Consumer to combine~>");
        c3.accept("Hello Students");
        System.out.println("------------------------");
    }

}
```

**Output**:

```
Using 'isNull' Predicate~>
For null = true
For 'Hello Students' = false
------------------------
Using 'isEmpty' Predicate~>
For empty = true
For 'Hello Students' = false
------------------------
Using 'isNotNullOrEmpty' Predicate~>
For null = false
For empty = false
For 'Hello Students' = true
------------------------
Using 'andThen' Consumer to combine~>
c1 consumer prints as upper case: HELLO STUDENTS
c2 consumer prints as lower case: hello students
------------------------
```

#### Interview Problem 2 (Societe Generale - Follow up for Problem 1): Demonstrate combining lambdas

Given a POJO class:

```java
public class Student {

    private final String name;
    private final int age;

    public Student(final String name, final int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

}
```

- Sort student names as natural ordering and print
- Sort student names as its length and print
- Sort students by 'Name' and then 'Age' in descending order and print

**Solution**:

```java
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.ToIntFunction;

public class CombiningLambdas {

    public static void main(final String[] args) {
        final var john = new Student("John", 18);
        final var mary = new Student("Mary", 16);
        final var thomas = new Student("Thomas", 21);
        final var rahul = new Student("Rahul", 23);
        final var jenny = new Student("Jenny", 17);
        final var tatiana = new Student("Tatiana", 25);
        final var john1 = new Student("John", 19);

        final var students = Arrays.asList(john, mary, thomas, rahul, jenny, tatiana, john1);

        final List<String> studentNames = new ArrayList<>();
        final Function<Student, String> toName = (Student student) -> student.getName();
        students.forEach(student -> {
            final String name = toName.apply(student); // Function mapping
            studentNames.add(name);
        });

        final Comparator<String> cmp = (s1, s2) -> s1.compareTo(s2);
        studentNames.sort(cmp);
        System.out.printf("Sorted student names as natural ordering: %s%n", studentNames);

        final ToIntFunction<String> toLength = s -> s.length(); // no boxing or unboxing done
        final Comparator<String> cmp2 = Comparator.comparingInt(toLength); // combining Comparator and Function
        studentNames.sort(cmp2);
        System.out.printf("Sorted student names as its length: %s%n", studentNames);
        System.out.println("-------------------------");

        // Comparators chaining and combining
        final Comparator<Student> cmpName = Comparator.comparing(user -> user.getName());
        final Comparator<Student> cmpAge = Comparator.comparing(user -> user.getAge());
        final Comparator<Student> cmpNameAndThenAge = cmpName.thenComparing(cmpAge);
        final Comparator<Student> reversed = cmpNameAndThenAge.reversed();

        students.sort(reversed);
        System.out.println("Printing Students sorted by 'Name' and then 'Age' in descending order~>");
        students.forEach(student -> System.out.println(student));
        System.out.println("-------------------------");
    }

}
```

**Output**:

```
Sorted student names as natural ordering: [Jenny, John, John, Mary, Rahul, Tatiana, Thomas]
Sorted student names as its length: [John, John, Mary, Jenny, Rahul, Thomas, Tatiana]
-------------------------
Printing Students sorted by 'Name' and then 'Age' in descending order~>
Student{name='Thomas', age=21}
Student{name='Tatiana', age=25}
Student{name='Rahul', age=23}
Student{name='Mary', age=16}
Student{name='John', age=19}
Student{name='John', age=18}
Student{name='Jenny', age=17}
-------------------------
```

---

### Chapter 02. Map Filter Reduce Algorithm

The three methods, `map`, `filter`, and `reduce`, are the cornerstone of any functional programming.

Usually, our data pipelines consist of one or more intermediate operations, transforming (aka **mapping**) and/or
**filtering** elements, and a terminal operation to gather the data again (aka **reducing**).

For ex: Suppose we have a group of students in a class, and we want to know the average age of students who are older
than 18 years.

- `map()`

  List<Student> ==> Student object **mapped** to Integer age ==> List<Integer>

- `filter()`

  List<Integer> ==> Filter all ages who are older than 18 years, discard all the ages less than 18 ==> List<Integer>

- `reduce()`

  List<Integer> ==> reduce all the ages to **one single value**, which is **average** (other reduce operations are
  **sum**, **min**, **max**, etc.)

In SQL, same could be written as:

```roomsql
SELECT AVG(age) 
FROM Students 
WHERE age >= 18
```

In Java 8, with introduction of Streams API, same could be written as:

```
List<Student> students = ...

students.stream()
           .mapToInt(student -> student.getAge())
           .filter(age -> age >= 18)
           .average();
```

The most important point about using Stream API is that the data is **never duplicated**. In the above example,
`students.stream()` is just creating an **empty** `Stream<Student>` object.

Looking at the example again:

```
students.stream()                                  // Stream<Student>
           .mapToInt(student -> student.getAge())  // IntStream
           .filter(age -> age >= 18)               // IntStream 
           .average();                             // Reduce - triggers the computation
```

Thus, there are 2 kinds of methods in Stream API:

- methods that create a new Stream => called **intermediate** methods
- methods that produce a result => called **terminal** methods

Only terminal methods trigger the computation, otherwise the intermediate methods are **lazy** and will be computed only
after the terminal method is triggered.

Example code:

```java
public class Student {

    private final String name;
    private final int age;

    public Student(final String name, final int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

}
```

Example for `MapFilterReduceDemo`:

```java
import java.util.Arrays;

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
    }

}
```

One more mapping function is available which is similar to `map()` but functionality is slightly different:

- `flatMap()`

  The `flatMap` method lets us replace each value of a stream with another stream and then concatenates all the
  generated streams into a single stream.

Example:

If `path` is the path to a file, then the following produces a stream of the words contained in that file:

     Stream<String> lines = Files.lines(path, StandardCharsets.UTF_8);
     Stream<String> words = lines.flatMap(line -> Stream.of(line.split(" +")));

The mapper function passed to `flatMap` splits a line, using a simple regular expression, into an array of words, and
then creates a stream of words from that array.

If we use `map()` instead of `flatMap()` above, it will return `Stream<Stream<String>>` instead of `Stream<String>`:

     Stream<Stream<String>> mappedLines = lines.map(line -> Stream.of(line.split(" +")));

Example code:

```java
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
```

In this code snippet:

```
        // Flat Map
        final var advancedJava = new Course("Advanced Java", john, mary);
        final var python = new Course("Python", thomas, rahul);
        final var algorithms = new Course("Algorithms", jenny, tatiana);

        final var courses = List.of(advancedJava, python, algorithms);
        courses.stream()
               .flatMap(course -> course.getStudents().stream())
               .map(p -> p.getName())
               .forEach(name -> System.out.println(name));
```

If we use `map(course -> course.getStudents().stream())`, this will result in `Stream<Stream<Student>>` but we want
`Stream<Student>` instead => thus `flatMap()` is used.

Complete code:

```java
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
```

Output:

```
Total no of students older than 20 years of age: 3
Total no of students less than 20 years of age: 3
John
Mary
Thomas
Rahul
Jenny
Tatiana
```

#### Interview Problem 3 (Merrill Lynch): Flat Map problem

Given the below code snippet:

```
final var students = List.of("John", "Mary", "Peter");
final var favoriteLanguages = List.of("Java", "Python");
```

Task: return pair of both the lists =>

```
[("John", "Java"), ("John", "Python"), ("Mary", "Java"), ("Mary", "Python"), ("Peter", "Java"), ("Peter", "Python")]
```

**Solution**:

We could use two maps to iterate on the two lists and generate the pairs. But this would return a
`Stream<Stream<String[]>>`. What we need to do is `flatten` the generated streams to result in a `Stream<String[]>`.

Complete code solution:

```java
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
```

Output:

```
(John,Java)
(John,Python)
(Mary,Java)
(Mary,Python)
(Peter,Java)
(Peter,Python)
```

---

### Chapter 03. Building a Stream

#### Creating a Stream from array

We can create a Stream from an array using:

- Factory method `Arrays.stream()`
- Factory method `Stream.of()`

Example code:

```java
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
```

Output:

```
Student{name='John', age=18}
Student{name='Mary', age=16}
Student{name='Thomas', age=21}
Student{name='Rahul', age=23}
Student{name='Jenny', age=17}
Student{name='Tatiana', age=25}
-----------------------------
Student{name='John', age=18}
Student{name='Mary', age=16}
Student{name='Thomas', age=21}
Student{name='Rahul', age=23}
Student{name='Jenny', age=17}
Student{name='Tatiana', age=25}
```

#### Creating a Stream from Collection

All the Java collections have `stream()` method to create a `Stream`

Code snippet:

```
final List<Student> students = Arrays.asList(john, mary, thomas, rahul, jenny, tatiana);
students.stream().forEach(System.out::println);
```

#### Creating a Stream from Text File

We can first create a `Path` to a file. And then, can use factory method `Files.lines()` to get `Stream<String>`.

Example code:

```java
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class StreamFromTextFile {

    public static void main(final String[] args) {
        final Path path = Path.of("src", "main", "resources", "200words.txt");
        try (final Stream<String> lines = Files.lines(path)) {
            final long count = lines.count();
            System.out.printf("Count = %d%n", count);
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

}
```

Output:

```
Count = 200
```

#### Creating a Stream using Regular Expression

We can first create a `Pattern` for a regular expression. And then, call `pattern.splitAsStream()` to return
`Stream<String>`.

Example code:

```java
import java.util.regex.Pattern;

public class StreamFromRegEx {

    public static void main(final String[] args) {
        final String sentence = "Life is like riding a bicycle. To keep your balance, you must keep moving.";
        final Pattern pattern = Pattern.compile("\\s");
        final long count = pattern.splitAsStream(sentence).count();
        System.out.printf("Count = %d%n", count);
    }

}
```

Output:

```
Count = 14
```

#### Creating a Stream from String

If we want to treat each String characters, we can use `String.chars()` method which returns `IntStream` for each
character code point. This is very handy for manipulating or doing certain operations on individual String characters
like sorting, finding distinct characters, etc.

```java
public class StreamFromString {

    public static void main(final String[] args) {
        final String sentence = "Life is like riding a bicycle. To keep your balance, you must keep moving.";

        sentence.chars()                                // IntStream
                .mapToObj(Character::toString)          // Stream<String>
                .filter(letter -> !letter.equals(" "))
                .distinct()
                .sorted()
                .forEach(System.out::print);
    }

}
```

Output:

```
,.LTabcdefgiklmnoprstuvy
```

#### Selecting elements of a Stream

We can select certain elements of a Stream using two patterns:

- get the index and call `skip()` or `limit()`
- use a `Predicate` and call `takeWhile()` or `dropWhile()`

Example code using `skip()` and `limit()`:

```java
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamSkippingAndLimiting {

    public static void main(final String[] args) {
        IntStream.range(0, 30)
                 .skip(10)
                 .limit(10)
                 .forEach(index -> System.out.printf("%d ", index));

        System.out.println("\n-------------------");

        final Path path = Path.of("src", "main", "resources", "200words.txt");
        try (final Stream<String> lines = Files.lines(path)) {
            lines.skip(20).limit(10).forEach(System.out::println);
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

}
```

Output:

```
10 11 12 13 14 15 16 17 18 19 
-------------------
at
but
be
my
on
have
him
is
said
me
```

Example code using `Predicate` and call `takeWhile()` and `dropWhile()`:

```java
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
```

Output:

```
4 4 4 5 6 7 
-------------------
8 9 10 
```

#### Converting a for loop to a Stream

Any `for` loop can be converted to Stream.

**Example**

Suppose we want to calculate the average age of Students who are greater than 20 years of age.

Using For-Loop:

```
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
```

However, the same calculation can be done using Streams very concisely:

```
    private static double getAverageAgeUsingStreams(final List<Student> students) {
        return students.stream()
                       .mapToInt(Student::getAge)
                       .filter(age -> age > 20)
                       .average()
                       .orElseThrow();
    }
```

Moreover, few operations can be done in parallel using `parallelStream()` if the calculation can be divided into
subtasks.

**Complete code**

```java
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
```

**Output:**

```
Average age using For-Loop = 23.00
Average age using Streams = 23.00
```

We should be cautious about one thing while converting a **For-Loop** to **Streams**:

- A **Stream** does one thing at a time
- So a **For-Loop** that does (say) 3 things -> should be converted into 3 streams

Thus, we can refactor from one big **For-Loop** doing multiple data processing steps into independent smaller unit steps
and convert each into Streams for better readability and even performance improvement (in case we can use parallel
streams).

---

### Chapter 04. Reducing Data using Stream

**Reduction** stream operations allow us to produce **one single result** from a sequence of elements, by repeatedly
applying a **combining** operation to the elements in the sequence.

Key concepts:

- **Identity** – an element that is the initial value of the reduction operation and the **default** result if the
  stream is empty
- **Accumulator** – a function that takes **two** parameters: a partial result of the reduction operation and the next
  element of the stream
- **Combiner** – a function used to combine the partial result of the reduction operation when the reduction is
  **parallelized** or when there's a mismatch between the types of the accumulator arguments and the types of the
  accumulator implementation

Example code snippet:

```
    @Test
    void testSumReduce() {
        final List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6);

        int result = numbers
                .stream()
                .reduce(0, (subtotal, element) -> subtotal + element);

        assertEquals(21, result);
    }
```

**Identity** = `0`

**Accumulator** = lambda expression => `subtotal, element -> subtotal + element`

We can also use:

```
        int result = numbers
                .stream()
                .reduce(0, Integer::sum);
```

**Using Parallel Stream**

```
    @Test
    void testSumReduceParallel() {
        final List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6);

        int result = numbers
                .parallelStream()
                .reduce(0, Integer::sum, Integer::sum);
                // .reduce(0, (subtotal, element) -> subtotal + element, Integer::sum);

        assertEquals(21, result);
    }
```

**Identity** = `0`

**Accumulator** = lambda expression => `subtotal, element -> subtotal + element`

**Combiner** = lambda expression => `Integer::sum`

When a stream executes in **parallel**, the Java runtime splits the stream into multiple sub-streams.

In such cases, we need to use a function to combine the results of the sub-streams into a single one.

This is the role of the **combiner** — in the above unit test, it's the `Integer::sum` method reference.

**Reducing in parallel**

When we use parallelized streams, we should make sure that `reduce()` or any other aggregate operations executed on the
streams are:

- **associative**: the result is not affected by the **order** of the operands
- **non-interfering**: the operation doesn't affect the data source
- **stateless and deterministic**: the operation doesn't have **state** and produces the same output for a given input

We should fulfill all these conditions to prevent unpredictable results.

As expected, operations performed on parallelized streams, including `reduce()`, are executed in parallel, hence taking
advantage of multicore hardware architectures.

**Reduction without identity**

If the reduction operation has an identity element, then it can be passed to the `reduce()` method. Thus, if the
processed stream is empty, then the identity element is returned.

```
        final List<Student> students = ...;
        int sum = students.stream()
                       .mapToInt(Student::getAge)
                       .filter(age -> age > 20)
                       .reduce(0, Integer::sum);
```

If the reduction operation has **NO** identity element, OR, if no element is provided, then the `reduce()` method wraps
the result in an `Optional` object.

```
        final List<Student> students = ...;
        final Optional<Integer> optionalSum = students.stream()
                       .mapToInt(Student::getAge)
                       .filter(age -> age > 20)
                       .reduce(Integer::sum);
```

We can check if an optional holds a value with `isPresent()` or `isEmpty()` and get its value using `get()` (Java 8)
or `orElseThrow()` (Java 11). Both these getter methods throw `NoSuchElementException` if the optional is empty.

Reduction methods that return `Optional`:

- `reduce(BinaryOperator)`
- `min()`
- `max()`
- `average()`

---

### Chapter 05. Collecting data from Stream

`Stream.collect()` is a terminal methods that allows us to perform mutable **** operations =>

- repackaging elements to some data structures (list, set, etc) and
- applying some additional logic, concatenating them, etc. on data elements held in a `Stream` instance

All predefined implementations can be found in the `Collectors` class:

```
import static java.util.stream.Collectors.*;
```

Here is a comprehensive code snippets for collecting data from `Stream` using `Collectors`:

```java
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

    }
}
```

#### GroupingBy Collector

The GroupingBy Collector:

- groups data using a function
- by default, collects the objects in list
- a downstream collector can be also passed

**Example:**

```java
public class Student {

    private final String name;
    private final int age;
    private final String course;

    public Student(final String name, final int age, final String course) {
        this.name = name;
        this.age = age;
        this.course = course;
    }

    // getters and equals() / hashcode()
}
```

Suppose I want to know which students have enrolled for a **Java** course or a **Python** course.

We can use `Collectors.groupingBy()`:

```java
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GroupingByDemo {

    public static void main(String[] args) {
        final var john = new Student("John", 18, "Python");
        final var mary = new Student("Mary", 16, "Java");
        final var thomas = new Student("Thomas", 21, "Java");
        final var rahul = new Student("Rahul", 23, "JavaScript");
        final var jenny = new Student("Jenny", 17, "Python");
        final var tatiana = new Student("Tatiana", 25, "Java");

        final List<Student> students = List.of(john, mary, thomas, rahul, jenny, tatiana);

        final Map<String, List<Student>> studentsPerCourse =
                students.stream()
                        .collect(
                                Collectors.groupingBy(
                                        Student::getCourse
                                                     )
                                );

        studentsPerCourse.forEach((course, st) -> System.out.printf("Course: %s, Students Enrolled: %s%n",
                                                                    course,
                                                                    st));
    }

}
```

**Output:**

```
Course: Java, Students Enrolled: [Student{name='Mary', age=16}, Student{name='Thomas', age=21}, Student{name='Tatiana', age=25}]
Course: JavaScript, Students Enrolled: [Student{name='Rahul', age=23}]
Course: Python, Students Enrolled: [Student{name='John', age=18}, Student{name='Jenny', age=17}]
```

Now, if I want to count how many number of students are enrolled to a course.

I can use a **downstream collector** as the overloaded argument to `Collectors.groupingBy()`

```
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
```

Here, `Collectors.counting()` is the downstream collector.

**Output:**

```
Course: Java, Number of Students Enrolled: 3
Course: JavaScript, Number of Students Enrolled: 1
Course: Python, Number of Students Enrolled: 2
```

**Collectors.partitioningBy()**

**PartitioningBy** is a specialized case of `groupingBy` that accepts a `Predicate` instance and then collects `Stream`
elements into a `Map` instance that stores `Boolean` values as **keys** and **collections** as **values**.

Under the `true` key, we can find a collection of elements matching the given `Predicate`, and under the `false` key, we
can find a collection of elements **not** matching the given `Predicate`.

```
        // Collectors.partitioningBy()
        final Map<Boolean, List<Student>> collectorsPartitioningBy
                = students.stream()
                          .collect(Collectors.partitioningBy(student -> student.getAge() > 20));
```
