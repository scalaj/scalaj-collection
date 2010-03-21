package scalaj
package collection
package j2s

import java.{lang => jl, util => ju}
import scala.{collection => sc}
import scala.collection.{immutable => sci, mutable => scm}

object Wrappers {
  def EnumerationWrapper[A, B](underlying: ju.Enumeration[A])(implicit convert: Convertible0[A, B]) = new EnumerationWrapper[A, B](underlying)
  def IteratorWrapper[A, B](underlying: ju.Iterator[A])(implicit convert: Convertible0[A, B]) = new IteratorWrapper[A, B](underlying)
  def IterableWrapper[A, B](underlying: jl.Iterable[A])(implicit convert: Convertible0[A, B]) = new IterableWrapper[A, B](underlying)
  def CollectionWrapper[A, B](underlying: ju.Collection[A])(implicit convert: Convertible0[A, B]) = new CollectionWrapper[A, B](underlying)
  def ListWrapper[A, B](underlying: ju.List[A])(implicit convert: Convertible0[A, B]) = new ListWrapper[A, B](underlying)
  def MutableListWrapper[A, B](underlying: ju.List[A])(implicit convert: Biconvertible0[A, B]) = new MutableListWrapper[A, B](underlying)
  def SetWrapper[A, B](underlying: ju.Set[A])(implicit convert: Biconvertible0[A, B]) = new SetWrapper[A, B](underlying)
  def MutableSetWrapper[A, B](underlying: ju.Set[A])(implicit convert: Biconvertible0[A, B]) = new MutableSetWrapper[A, B](underlying)
  // def MapWrapper[A, B](underlying: ju.Map[A, B]) = new MapWrapper(underlying)
  // def MutableMapWrapper[A, B](underlying: ju.Map[A, B]) = new MutableMapWrapper(underlying)
}

class EnumerationWrapper[A, B](val underlying: ju.Enumeration[A])(implicit convert: Convertible0[A, B]) extends sc.Iterator[B] {
  override def next: B = convert(underlying.nextElement)
  override def hasNext: Boolean = underlying.hasMoreElements
}

class IteratorWrapper[A, B](val underlying: ju.Iterator[A])(implicit convert: Convertible0[A, B]) extends sc.Iterator[B] {
  override def next: B = convert(underlying.next)
  override def hasNext: Boolean = underlying.hasNext
}

class IterableWrapper[A, B](val underlying: jl.Iterable[A])(implicit convert: Convertible0[A, B]) extends sc.Iterable[B] {
  override def iterator: sc.Iterator[B] = new IteratorWrapper(underlying.iterator)
}

class CollectionWrapper[A, B](override val underlying: ju.Collection[A])(implicit convert: Convertible0[A, B]) extends IterableWrapper[A, B](underlying)

class ListWrapper[A, B](override val underlying: ju.List[A])(implicit convert: Convertible0[A, B]) extends CollectionWrapper[A, B](underlying) with sc.Seq[B] {
  override def apply(index: Int): B = convert(underlying.get(index))
  override def length: Int = underlying.size
}

class MutableListWrapper[A, B](override val underlying: ju.List[A])(implicit convert: Biconvertible0[A, B]) extends ListWrapper[A, B](underlying) with scm.Seq[B] {
  override def update(index: Int, element: B): Unit = underlying.set(index, convert.reverse(element))
}

abstract class AbstractSetWrapper[A, B](override val underlying: ju.Set[A])(implicit convert: Biconvertible0[A, B]) extends CollectionWrapper[A, B](underlying) with sc.Set[B] {
  override def contains(key: B): Boolean = underlying.contains(convert.reverse(key))
}

class SetWrapper[A, B](override val underlying: ju.Set[A])(implicit convert: Biconvertible0[A, B]) extends AbstractSetWrapper[A, B](underlying) {
  override def - (elem: B): sc.Set[B] = empty ++ this - elem
  override def + (elem: B): sc.Set[B] = empty ++ this + elem
}

class MutableSetWrapper[A, B](override val underlying: ju.Set[A])(implicit convert: Biconvertible0[A, B]) extends AbstractSetWrapper[A, B](underlying) with scm.Set[B] {
  override def -= (elem: B): this.type = {
    underlying.remove(convert.reverse(elem))
    this
  }
  override def += (elem: B): this.type = {
    underlying.add(convert.reverse(elem))
    this
  }
}

abstract class AbstractMapWrapper[A, B, C, D](val underlying: ju.Map[A, B])(implicit convertA: Biconvertible0[A, C], convertB: Convertible0[B, D]) extends sc.Map[C, D] {
  override def iterator: sc.Iterator[(C, D)] =
    new IteratorWrapper(underlying.entrySet.iterator).map(entry => (convertA(entry.getKey), convertB(entry.getValue)))
  override def get(key: C): Option[D] = underlying.get(convertA.reverse(key)) match {
    case null => if (underlying.containsKey(convertA.reverse(key))) Some(null.asInstanceOf[D]) else None
    case value => Some(convertB(value))
  }
}

class MapWrapper[A, B, C, D](override val underlying: ju.Map[A, B])(implicit convertA: Biconvertible0[A, C], convertB: Convertible0[B, D]) extends AbstractMapWrapper[A, B, C, D](underlying) {
  override def - (key: C): sc.Map[C, D] = empty ++ this - key
  override def + [D1 >: D](kv: (C, D1)): sc.Map[C, D1] = empty ++ this + (kv)
}

class MutableMapWrapper[A, B, C, D](override val underlying: ju.Map[A, B])(implicit convertA: Biconvertible0[A, C], convertB: Biconvertible0[B, D]) extends AbstractMapWrapper[A, B, C, D](underlying) with scm.Map[C, D] {
  override def -= (key: C): this.type = {
    underlying.remove(convertA.reverse(key))
    this
  }
  override def += (kv: (C, D)): this.type = {
    underlying.put(convertA.reverse(kv._1), convertB.reverse(kv._2))
    this
  }
}
