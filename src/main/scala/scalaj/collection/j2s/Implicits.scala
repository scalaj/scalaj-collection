package scalaj
package collection
package j2s

import java.{lang => jl, util => ju}
import scala.{collection => sc}
import scala.collection.{immutable => sci, mutable => scm}

object Implicits extends Implicits

trait Implicits {
  implicit def RichJComparable[A](underlying: jl.Comparable[A]): RichComparable[A] = new RichComparable(underlying)
  implicit def RichJComparator[A](underlying: ju.Comparator[A]): RichComparator[A] = new RichComparator(underlying)
  implicit def RichJEnumeration[A](underlying: ju.Enumeration[A]): RichEnumeration[A] = new RichEnumeration(underlying)
  implicit def RichJIterator[A](underlying: ju.Iterator[A]): RichIterator[A] = new RichIterator(underlying)
  implicit def RichJIterable[A](underlying: jl.Iterable[A]): RichIterable[A] = new RichIterable(underlying)
  implicit def RichJList[A](underlying: ju.List[A]): RichList[A] = new RichList(underlying)
  implicit def RichJSet[A](underlying: ju.Set[A]): RichSet[A] = new RichSet(underlying)
  implicit def RichJMap[A, B](underlying: ju.Map[A, B]): RichMap[A, B] = new RichMap(underlying)
  implicit def RichJDictionary[A, B](underlying: ju.Dictionary[A, B]): RichDictionary[A, B] = new RichDictionary(underlying)
}

sealed trait Coercible[-A, +B] {
  def apply(x: A): B
}

object Coercible extends LowPriorityCoercible {
  private[collection] def coerce[M[_], A, B](m: M[A])(implicit c: Coercible[A, B]): M[B] = m.asInstanceOf[M[B]]
  private[collection] def coerce2[M[_, _], A, B, C, D](m: M[A, B])(implicit c1: Coercible[A, C], c2: Coercible[B, D]): M[C, D] = m.asInstanceOf[M[C, D]]

  implicit object CoercibleBoolean extends PrimitiveCoercible[jl.Boolean, Boolean]
  implicit object CoercibleChar extends PrimitiveCoercible[jl.Character, Char]
  implicit object CoercibleByte extends PrimitiveCoercible[jl.Byte, Byte]
  implicit object CoercibleShort extends PrimitiveCoercible[jl.Short, Short]
  implicit object CoercibleInt extends PrimitiveCoercible[jl.Integer, Int]
  implicit object CoercibleLong extends PrimitiveCoercible[jl.Long, Long]
  implicit object CoercibleFloat extends PrimitiveCoercible[jl.Float, Float]
  implicit object CoercibleDouble extends PrimitiveCoercible[jl.Double, Double]

  private[Coercible] class PrimitiveCoercible[A, B] extends Coercible[A, B] {
    override def apply(x: A): B = x.asInstanceOf[B]
  }
}

trait LowPriorityCoercible {
  implicit def CoercibleSelf[A]: Coercible[A, A] = CoercibleSelfObject.asInstanceOf[Coercible[A, A]]

  private object CoercibleSelfObject extends Coercible[Any, Any] {
    override def apply(x: Any): Any = x
  }
}
