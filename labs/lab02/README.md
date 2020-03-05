# Lab 2, CSC/CPE 203 - static methods v. instance methods

## Orientation

This lab explores writing static methods versus instance methods in Java. Both are useful ways to get
something done (a computation), but as we develop more complex code, there are lots of reasons to
strongly associate a computation with a specific instance of an object (associate data and computation
together in one object).

## Objectives

- To be able to write static methods that can compute the perimeter of three different types of
    geometric shapes (Circle, Rectangle and Polygon)(part 1)
- To be able to write instance methods to compute the perimeter of three different types of
    geometric shapes (part 2)
- To be able to write a static method to compare the perimeters of different objects (part 1 & 2)
- To be able to write your own test cases to create geometric shapes and test the computation of
    their perimeter, and the computation of comparison of their sizes (measured as perimeter) (part 1
    & 2)

## Resources

Same as the last two labs

You can get the base code on Polylearn

## Part 1: Classes and Overloaded Methods

In the provided part1 subdirectory, implement the following classes with these general requirements:

- Each instance variable must be private and final.
- Provide the appropriate "accessor"/"getter" methods for the instance variables. (see
    PartOneTestCases.java for names)
- You may add methods beyond those required, but any such additional method must be
    private.

Automaton Warning: You are an intelligent person; you should not just mechanically apply these rules
only to satisfy a style checker in order to complete the lab. Instead, consider the reasons for these rules,
what violating them exposes to the user/client of the corresponding class, and what following them
guarantees to you, the implementer. If you aren't sure, ask your neighbor, they might have thoughts of
their own.


### Classes

The following assumes your two-dimensional Point implementation from the previous lab. Copy your
Point.java into the working directory.

Implement these classes.

- Circle.java represents a circle given a Point center and a double radius.
- Rectangle.java represents an axis-aligned rectangle given a Point top-left and a Point
    bottom-right.
- Polygon.java represents a polygon given List<Point> points.

**Note that as your implementation will be tested with PartOneTestCases, you may want to read
exactly what the expected "get" method names are!**

### Overloaded Method

Define a Util class with three static perimeter methods. Each such method takes, as its single
parameter, one of the classes defined in the previous step (i.e., there is one perimeter method that
takes a Circle, one perimeter method that takes a Polygon, etc.) and returns the perimeter of the
object (as a double).

You can assume for now that all constructors will create correct closed shapes.

A method (or methods) like perimeter is considered overloaded since there is a separate definition for
different parameter types. This is also referred to as ad-hoc polymorphism because the (theoretically)
_single_ method is effectively defined to work with a small set of parameter types, not for all parameter
types (i.e., the method takes "many forms", but only very specific forms are supported). You will likely
hear "overloaded" much more often, but "ad-hoc polymorphism" sounds so much cooler (though,
admittedly, it is a bit more intimidating).

Note that to call a static method like these, you can invoke: Util.perimeter(new Circle(new
Point(0, 0), 1.0));

Deeper Understanding: How does the Java implementation know which version of perimeter to use
when the method is invoked (not-so-great hint at this point, the compiler determines the implementation
under this scenario)? If the answer is not apparent, then think about the different method invocations and
speak with those around you. If the answer seems obvious, then hold on to that belief for the next few
weeks to see if it continues to hold.

### Methods in Action

Define a Bigger class with one static whichIsBigger method. This helper method will take three
parameters a Circle, Rectangle, and Polygon and will return a double representing the largest
perimeter of the three objects. You will use this method to determine which is the largest between:

1. a cirle centered at {1.0, 1.0} with radius of 2.
2. a rectangle with the corners {-1.0, 2.0} and {1.0, -1.6}
3. a polygon defined by {0, 0}, {3, 1}, {1, 4}, and {-1, 4}


You may want to test your perimeter computations first (see next section).

### Tests

Yes, of course you will want to test all of these operations. Add your tests to the provided
PartOneTestCases.java file.

You are expected to write at least one test case for each perimeter and one test case for whichIsBigger.
**That means you must demo at least 8 tests (as there are some already there for implementation
details).**

Helper: here is an example of testing the perimeter for a polygon:
```java
@Test
public void testPerimPoly() {
List < Point >points = new ArrayList < Point >();
points.add(new Point(0, 0));
points.add(new Point(3,0));
points.add(new Point(0,4));
double d = Util.perimeter(new Polygon(points));
assertEquals(12.0, d, DELTA);
}
```

## Part 2: Methods

Copy your files from part1 into part2 (you will not need the test cases file from part 1).

The definition of perimeter in the first part of this lab does not follow an object-oriented style. This part
of the lab asks that you make a few modifications to improve the code (further such improvements can
come with later material). _Don't worry this part will be easier!_

From Util.java, move each perimeter method into the appropriate class (as a non-static method,
i.e., instance method) corresponding to perimeter's parameter. The goal is that each object "knows
how" to compute its own perimeter. As such, perimeter will no longer need to take a parameter (it acts
on this which refers to ... well, there is no universally accepted term for the target of this because
computer scientists are not very good at naming things or at agreeing on the meaning of names/terms;
you might hear "calling object", "current object", "context object", "target", "callee", "referent", "object on
which the method was invoked" (that last one is real, and accurate, but is a sign of giving up on naming)).
(You can remove Util.java once this is done).

Be sure to also copy over the Bigger class and revise it to use your new instance methods. _Take a
moment... do you like one style over the other?_

### Tests

Add tests to the provided PartTwoTestCases.java file. This is where you will also be able to see the
changes of having the methods defined within each class (instance methods) versus the static methods


this lab started with. **Again, there should be an added test for each of the types of perimeters and a
test of whichIsBigger (can you challenge your neighbor with new perimeters?)** _Again, reflect, do
you like one style over the other?_

## Submission and Demo

Be sure to commit the code by the end of the week. Your instructor reserves the right to run further tests
on your code. Demonstrate **both** parts of your working program to your instructor at once. Be prepared to
show your source code. You will need to demo your working code the last lab day in week2. (Partial credit
available up to instrutor discretion).
