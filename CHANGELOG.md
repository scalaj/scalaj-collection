## Release Notes

# 1.5

- Implement equals, hashCode, toString in LinearSeqWrapper

# 1.4

- Support for Collection<E>#toArray(T[])T[] in LinearSeqWrapper

# 1.3

- Drop support for Scala 2.8.x
- Binary compatible with Scala 2.9.2 and 2.10
- Remove @serializable annotation (deprecated)
- Upgrade to sbt 0.12.4
- Performance optimizations for LinearSeq

# 1.2

- Binary compatibility with Scala 2.9.1

# 1.1

- Binary compatibility with Scala 2.9.0 and 2.9.0-1

# 1.0

- Turn any Scala collection into the corresponding Java collection
- Turn any Java collection into the corresponding Scala collection
- Use `foreach` on any Java collection
- Correctly handle primitives, Comparable, Comparator, Ordered, and Ordering

# 1.0.Beta2

- Changed signature of Map#foreach to take a function of a Tuple2 rather than a Function2
- Added support for Comparable, Comparator, Ordered, and Ordering
- Coercible no longer subclasses Function1 (to prevent accidentally putting implicit conversions in scope)
- Wrappers are now @serializable if the underlying collection is @serializable

Special thanks to Daniel Spiewak and Michel Salim, who provided the feedback that led to most of the improvements in this release.

# 1.0.Beta1

- Port of scala-javautils to Scala 2.8.0.Beta1
- Now with a better package name: scalaj.collection
- Also supports bidirectional conversion between Java boxed types (java.lang.Integer) and Scala primitive types (scala.Int)
