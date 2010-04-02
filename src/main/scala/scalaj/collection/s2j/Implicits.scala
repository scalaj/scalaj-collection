package scalaj
package collection
package s2j

import java.{lang => jl, util => ju}
import scala.{collection => sc, math => sm}
import scala.collection.{immutable => sci, mutable => scm}

object Implicits extends Implicits

trait Implicits {
  implicit def RichSOrdered[A](underlying: sm.Ordered[A]): RichOrdered[A] = new RichOrdered(underlying)
  implicit def RichSOrdering[A](underlying: sm.Ordering[A]): RichOrdering[A] = new RichOrdering(underlying)
  implicit def RichSIterator[A](underlying: sc.Iterator[A]): RichIterator[A] = new RichIterator(underlying)
  implicit def RichSIterable[A](underlying: sc.Iterable[A]): RichIterable[A] = new RichIterable(underlying)
  implicit def RichSSeq[A](underlying: sc.Seq[A]): RichSeq[A] = new RichSeq(underlying)
  implicit def RichSMutableSeq[A](underlying: scm.Seq[A]): RichMutableSeq[A] = new RichMutableSeq(underlying)
  implicit def RichSBuffer[A](underlying: scm.Buffer[A]): RichBuffer[A] = new RichBuffer(underlying)
  implicit def RichSSet[A](underlying: sc.Set[A]): RichSet[A] = new RichSet(underlying)
  implicit def RichSMutableSet[A](underlying: scm.Set[A]): RichMutableSet[A] = new RichMutableSet(underlying)
  implicit def RichSMap[A, B](underlying: sc.Map[A, B]): RichMap[A, B] = new RichMap(underlying)
  implicit def RichSMutableMap[A, B](underlying: scm.Map[A, B]): RichMutableMap[A, B] = new RichMutableMap(underlying)
}

sealed trait Coercible[-A, +B] {
  def apply(x: A): B
}

object Coercible extends LowPriorityCoercible {
  private[collection] def coerce[M[_], A, B](m: M[A])(implicit c: Coercible[A, B]): M[B] = m.asInstanceOf[M[B]]
  private[collection] def coerce2[M[_, _], A, B, C, D](m: M[A, B])(implicit c1: Coercible[A, C], c2: Coercible[B, D]): M[C, D] = m.asInstanceOf[M[C, D]]

  implicit object CoercibleBoolean extends PrimitiveCoercible[Boolean, jl.Boolean]
  implicit object CoercibleChar extends PrimitiveCoercible[Char, jl.Character]
  implicit object CoercibleByte extends PrimitiveCoercible[Byte, jl.Byte]
  implicit object CoercibleShort extends PrimitiveCoercible[Short, jl.Short]
  implicit object CoercibleInt extends PrimitiveCoercible[Int, jl.Integer]
  implicit object CoercibleLong extends PrimitiveCoercible[Long, jl.Long]
  implicit object CoercibleFloat extends PrimitiveCoercible[Float, jl.Float]
  implicit object CoercibleDouble extends PrimitiveCoercible[Double, jl.Double]

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
