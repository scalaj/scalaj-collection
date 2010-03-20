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
