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
