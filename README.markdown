# scalaj-collection

A library for converting between Java and Scala collections.

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

    val list = new java.util.ArrayList[java.lang.Integer]
    list.add(1)
    list.add(2)
    list.add(3)
    list.asScala
    // returns scala.Seq[Int]

As shown in the above examples, the library also converts between the boxed types of Scala and Java primitives.

You can also use foreach on any Java collection:

    list.foreach(print)
    // prints "123"

The `map`, `filter`, and `flatMap` methods are still pending.

## Requirements

scalaj-collection requires Scala 2.9.x or Scala 2.10.x.

## Installation

### sbt

If you're using sbt, simply add the following line to your project file:

    libraryDependencies += "org.scalaj" %% "scalaj-collection" % "1.3"

### Maven

If you're using Maven, add the following to your pom.xml:

    <dependency>
      <groupId>org.scalaj</groupId>
      <artifactId>scalaj-collection_${scala.version}</artifactId>
      <version>1.3</version>
    </dependency>

### JARs

Download the jars from [sonatype.org](https://oss.sonatype.org/content/repositories/releases/org/scalaj/)

### From source

Clone the repository from Github:

    git clone git://github.com/scalaj/scalaj-collection.git

Build the project and create the JAR (requires [sbt](http://www.scala-sbt.org/) version 0.12.4 or greater):

    cd scalaj-collection
    sbt package

## Documentation

The following methods are added via the [Pimp My Library](http://www.artima.com/weblogs/viewpost.jsp?thread=179766) pattern:

    // Java to Scala
    java.lang.Comparable[A]   #asScala: scala.math.Ordered[A]
    java.util.Comparator[A]   #asScala: scala.math.Ordering[A]
    java.util.Enumeration[A]  #asScala: scala.collection.Iterator[A]
                              #foreach(A => Unit): Unit
    java.util.Iterator[A]     #asScala: scala.collection.Iterator[A]
                              #foreach(A => Unit): Unit
    java.lang.Iterable[A]     #asScala: scala.collection.Iterable[A]
                              #foreach(A => Unit): Unit
    java.util.List[A]         #asScala: scala.collection.Seq[A]
                              #asScalaMutable: scala.collection.mutable.Seq[A]
    java.util.Set[A]          #asScala: scala.collection.Set[A]
                              #asScalaMutable: scala.collection.mutable.Set[A]
    java.util.Map[A, B]       #asScala: scala.collection.Map[A, B]
                              #asScalaMutable: scala.collection.mutable.Map[A, B]
                              #foreach(((A, B)) => Unit): Unit
    java.util.Dictionary[A, B]#asScala: scala.collection.mutable.Map[A, B]
                              #foreach(((A, B)) => Unit): Unit

    // Scala to Java
    scala.math.Ordered[A]             #asJava: java.util.Comparable[A]
    scala.math.Ordering[A]            #asJava: java.util.Comparator[A]
    scala.collection.Iterator[A]      #asJava: java.util.Iterator[A]
                                      #asJavaEnumeration: java.util.Enumeration[A]
    scala.collection.Iterable[A]      #asJava: java.lang.Iterable[A]
    scala.collection.Seq[A]           #asJava: java.util.List[A]
    scala.collection.mutable.Seq[A]   #asJava: java.util.List[A]
    scala.collection.mutable.Buffer[A]#asJava: java.util.List[A]
    scala.collection.Set[A]           #asJava: java.util.Set[A]
    scala.collection.mutable.Set[A]   #asJava: java.util.Set[A]
    scala.collection.Map[A, B]        #asJava: java.util.Map[A, B]
    scala.collection.mutable.Map[A, B]#asJava: java.util.Map[A, B]
                                      #asJavaDictionary: java.util.Dictionary[A, B]

