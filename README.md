# scalaj-collection

Utilities for converting between Java and Scala collections.

## Usage

Turn any Scala collection into the corresponding Java collection:

    import scalaj.collection.Imports._

    List(1, 2, 3).asJava
    // returns java.util.List[java.lang.Integer]

    Map(1 -> "a", 2 -> "b", 3 -> "c").asJava
    // returns java.util.Map[java.lang.Integer, java.lang.String]

    Set(1, 2, 3).asJava
    // returns java.util.Set[java.lang.Integer]

Turn any Java collection into the corresponding Scala collection:

    val list = new java.util.ArrayList[Int]
    list.add(1)
    list.add(2)
    list.add(3)
    list.asScala
    // returns scala.Seq[Int]

You can also use foreach on any Java collection:

    list.foreach(print)
    // prints "123"

The `map`, `filter`, and `flatMap` methods are still pending.

## Requirements

scalaj-collection requires at least Scala 2.8.0.Beta1 or later.

## Installation

<!--
### sbt

If you're using simple-build-tool, simply add the following line to your project file:

    val scalaj_collection = "org.scalaj" %% "collection" % "1.0.Beta1"

### Maven

If you're using Maven, add the following to your pom.xml:

    <dependency>
      <groupId>org.scalaj</groupId>
      <artifactId>collection</artifactId>
      <version>1.0.Beta1_${scala.version}</version>
    </dependency>

### JARs

Download the jars from http://scala-tools.org/repo-releases/org/scalaj/collection/
-->
### From source

Clone the repository from Github:

    git clone git://github.com/scalaj/scalaj-collection.git

Build the project and create the JAR (requires [sbt](http://code.google.com/p/simple-build-tool/) version 0.7.1 or greater):

    cd scalaj-collection
    sbt package

## Documentation

The following methods are added via the [Pimp My Library](http://www.artima.com/weblogs/viewpost.jsp?thread=179766) pattern:

    // Java to Scala
    java.util.Enumeration[A]#asScala: scala.collection.Iterator[A]
                            #foreach(A => Unit): Unit
    java.util.Iterator[A]#asScala: scala.collection.Iterator[A]
                         #foreach(A => Unit): Unit
    java.lang.Iterable[A]
    java.util.List[A]
    java.util.Set[A]
    java.util.Map[A, B]
    java.util.Dictionary[A, B]
    