package scalaj
package collection
package j2s

import java.{lang => jl, util => ju}
import scala.{collection => sc}
import scala.collection.{immutable => sci, mutable => scm}

object Implicits extends Implicits

trait Implicits {
  implicit def RichJEnumeration[A](underlying: ju.Enumeration[A]): RichEnumeration[A] = new RichEnumeration(underlying)
  implicit def RichJIterator[A](underlying: ju.Iterator[A]): RichIterator[A] = new RichIterator(underlying)
  implicit def RichJIterable[A](underlying: jl.Iterable[A]): RichIterable[A] = new RichIterable(underlying)
  implicit def RichJList[A](underlying: ju.List[A]): RichList[A] = new RichList(underlying)
  implicit def RichJSet[A](underlying: ju.Set[A]): RichSet[A] = new RichSet(underlying)
  implicit def RichJMap[A, B](underlying: ju.Map[A, B]): RichMap[A, B] = new RichMap(underlying)
}

trait Coercible[A, B] {
  def apply[M[X]](a: M[A]): M[B] = a.asInstanceOf[M[B]]
  def coerce2[M[X, Y], C, D](a: M[A, C])(implicit coerce: Coercible[C, D]): M[B, D] = a.asInstanceOf[M[B, D]]
}

object Coercible extends LowPriorityCoercible {
  implicit object CoercibleBoolean extends Coercible[jl.Boolean, Boolean]
  implicit object CoercibleChar extends Coercible[jl.Character, Char]
  implicit object CoercibleByte extends Coercible[jl.Byte, Byte]
  implicit object CoercibleShort extends Coercible[jl.Short, Short]
  implicit object CoercibleInt extends Coercible[jl.Integer, Int]
  implicit object CoercibleLong extends Coercible[jl.Long, Long]
  implicit object CoercibleFloat extends Coercible[jl.Float, Float]
  implicit object CoercibleDouble extends Coercible[jl.Double, Double]
}

trait LowPriorityCoercible {
  implicit def CoercibleSelf[A]: Coercible[A, A] = new Coercible[A, A] {}
}
