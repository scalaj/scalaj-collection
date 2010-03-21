package scalaj
package collection
package s2j

import java.{lang => jl, util => ju}
import scala.{collection => sc}
import scala.collection.{immutable => sci, mutable => scm}

object Implicits extends Implicits

trait Implicits {
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

trait Coercible[A, B] {
  def apply[M[X]](a: M[A]): M[B] = a.asInstanceOf[M[B]]
  def coerce2[M[X, Y], C, D](a: M[A, C])(implicit coerce: Coercible[C, D]): M[B, D] = a.asInstanceOf[M[B, D]]
}

object Coercible extends LowPriorityCoercible {
  implicit object CoercibleBoolean extends Coercible[Boolean, jl.Boolean]
  implicit object CoercibleChar extends Coercible[Char, jl.Character]
  implicit object CoercibleByte extends Coercible[Byte, jl.Byte]
  implicit object CoercibleShort extends Coercible[Short, jl.Short]
  implicit object CoercibleInt extends Coercible[Int, jl.Integer]
  implicit object CoercibleLong extends Coercible[Long, jl.Long]
  implicit object CoercibleFloat extends Coercible[Float, jl.Float]
  implicit object CoercibleDouble extends Coercible[Double, jl.Double]
}

trait LowPriorityCoercible {
  implicit def CoercibleSelf[A]: Coercible[A, A] = new Coercible[A, A] {}
}
