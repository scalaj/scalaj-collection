package scalaj
package collection
package j2s

import java.{lang => jl, util => ju}
import scala.{collection => sc}
import scala.collection.{immutable => sci, mutable => scm}

private[collection] class RichEnumeration[A](underlying: ju.Enumeration[A]) {
  def asScala: sc.Iterator[A] = underlying match {
    case w : s2j.IteratorWrapper[_] => w.underlying
    case _ => new EnumerationWrapper(underlying)
  }

  def foreach(f: A => Unit): Unit =
    Helpers.foreach(underlying, f)
}

private[collection] class RichIterator[A](underlying: ju.Iterator[A]) {
  def asScala: sc.Iterator[A] = underlying match {
    case w : s2j.IteratorWrapper[_] => w.underlying
    case _ => new IteratorWrapper(underlying)
  }

  def foreach(f: A => Unit): Unit =
    Helpers.foreach(underlying, f)
}

private[collection] class RichIterable[A](underlying: jl.Iterable[A]) {
  def asScala: sc.Iterable[A] = underlying match {
    case w : s2j.IterableWrapper[_] => w.underlying
    case _ => new IterableWrapper(underlying)
  }

  def foreach(f: A => Unit): Unit =
    Helpers.foreach(underlying.iterator, f)
}

private[collection] class RichList[A](underlying: ju.List[A]) {
  def asScala: sc.Seq[A] = underlying match {
    case w : s2j.SeqWrapper[_] => w.underlying
    case _ => new ListWrapper(underlying)
  }
  def asScalaMutable: scm.Seq[A] = underlying match {
    case w : s2j.MutableSeqWrapper[_] => w.underlying
    case _ => new MutableListWrapper(underlying)
  }
}

private[collection] class RichSet[A](underlying: ju.Set[A]) {
  def asScala: sc.Set[A] = underlying match {
    case w : s2j.SetWrapper[_] => w.underlying
    case _ => new SetWrapper(underlying)
  }
  def asScalaMutable: scm.Set[A] = underlying match {
    case w : s2j.MutableSetWrapper[_] => w.underlying
    case _ => new MutableSetWrapper(underlying)
  }
}

private[collection] class RichMap[A, B](underlying: ju.Map[A, B]) {
  def asScala: sc.Map[A, B] = underlying match {
    case w : s2j.MapWrapper[_, _] => w.underlying
    case _ => new MapWrapper(underlying)
  }
  def asScalaMutable: scm.Map[A, B] = underlying match {
    case w : s2j.MutableMapWrapper[_, _] => w.underlying
    case _ => new MutableMapWrapper(underlying)
  }
  
  def foreach(f: (A, B) => Unit): Unit = {
    val g = (e: ju.Map.Entry[A, B]) => f(e.getKey, e.getValue)
    Helpers.foreach(underlying.entrySet.iterator, g)
  }
}
