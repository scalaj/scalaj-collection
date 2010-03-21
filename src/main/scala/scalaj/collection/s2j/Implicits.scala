package scalaj
package collection
package s2j

import java.{lang => jl, util => ju}
import scala.{collection => sc}
import scala.collection.{immutable => sci, mutable => scm}

object Implicits extends Implicits

trait Implicits {
//   implicit def RichSIterator[A](underlying: sc.Iterator[A]): RichIterator[A] = new RichIterator(underlying)
//   implicit def RichSIterable[A](underlying: sc.Iterable[A]): RichIterable[A] = new RichIterable(underlying)
//   implicit def RichSSeq[A](underlying: sc.Seq[A]): RichSeq[A] = new RichSeq(underlying)
//   implicit def RichSMutableSeq[A](underlying: scm.Seq[A]): RichMutableSeq[A] = new RichMutableSeq(underlying)
//   implicit def RichSBuffer[A](underlying: scm.Buffer[A]): RichBuffer[A] = new RichBuffer(underlying)
//   implicit def RichSSet[A](underlying: sc.Set[A]): RichSet[A] = new RichSet(underlying)
//   implicit def RichSMutableSet[A](underlying: scm.Set[A]): RichMutableSet[A] = new RichMutableSet(underlying)
//   implicit def RichSMap[A, B](underlying: sc.Map[A, B]): RichMap[A, B] = new RichMap(underlying)
//   implicit def RichSMutableMap[A, B](underlying: scm.Map[A, B]): RichMutableMap[A, B] = new RichMutableMap(underlying)
}
// 
// class Convertible[A, B](f: A => B) extends (A => B) {
//   override def apply(x: A): B = f(x)
// }
// 
// object Convertible extends LowPriorityConvertible {
//   implicit object ConvertibleBoolean extends Convertible[jl.Boolean, Boolean](identity _)
//   implicit object ConvertibleChar extends Convertible[jl.Character, Char](identity _)
//   implicit object ConvertibleByte extends Convertible[jl.Byte, Byte](identity _)
//   implicit object ConvertibleShort extends Convertible[jl.Short, Short](identity _)
//   implicit object ConvertibleInt extends Convertible[jl.Integer, Int](identity _)
//   implicit object ConvertibleLong extends Convertible[jl.Long, Long](identity _)
//   implicit object ConvertibleFloat extends Convertible[jl.Float, Float](identity _)
//   implicit object ConvertibleDouble extends Convertible[jl.Double, Double](identity _)
// }
// 
// trait LowPriorityConvertible {
//   implicit def ConvertibleSelf[A]: Convertible[A, A] = new Convertible[A, A](identity _)
// }
