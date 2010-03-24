package scalaj
package collection
package j2s

import java.{lang => jl, util => ju}
import scala.{collection => sc, math => sm}
import scala.collection.{immutable => sci, mutable => scm}
import Coercible.{coerce, coerce2}

private[collection] class RichComparable[A](underlying: jl.Comparable[A]) {
  def asScala[B](implicit c: Coercible[A, B]): sm.Ordered[A] = underlying match {
    case s: sm.Ordered[_] => coerce(s)
    case _ => coerce(new ComparableWrapper(underlying))
  }
}

private[collection] class RichComparator[A](underlying: ju.Comparator[A]) {
  def asScala[B](implicit c: Coercible[A, B]): sm.Ordering[B] = underlying match {
    case s: sm.Ordering[_] => coerce(s)
    case _ => coerce(new ComparatorWrapper(underlying))
  }
}

private[collection] class RichEnumeration[A](underlying: ju.Enumeration[A]) {
  def asScala[B](implicit c: Coercible[A, B]): sc.Iterator[B] = underlying match {
    case w : s2j.IteratorWrapper[_] => coerce(w.underlying)
    case _ => coerce(new EnumerationWrapper(underlying))
  }

  def foreach(f: A => Unit): Unit =
    Helpers.foreach(underlying, f)
}

private[collection] class RichIterator[A](underlying: ju.Iterator[A]) {
  def asScala[B](implicit c: Coercible[A, B]): sc.Iterator[B] = underlying match {
    case w : s2j.IteratorWrapper[_] => coerce(w.underlying)
    case _ => coerce(new IteratorWrapper(underlying))
  }

  def foreach(f: A => Unit): Unit =
    Helpers.foreach(underlying, f)
}

private[collection] class RichIterable[A](underlying: jl.Iterable[A]) {
  def asScala[B](implicit c: Coercible[A, B]): sc.Iterable[B] = underlying match {
    case w : s2j.IterableWrapper[_] => coerce(w.underlying)
    case _ => coerce(new IterableWrapper(underlying))
  }

  def foreach(f: A => Unit): Unit =
    Helpers.foreach(underlying.iterator, f)
}

private[collection] class RichList[A](underlying: ju.List[A]) {
  def asScala[B](implicit c: Coercible[A, B]): sc.Seq[B] = underlying match {
    case w : s2j.SeqWrapper[_] => coerce(w.underlying)
    case _ => coerce(new ListWrapper(underlying))
  }
  def asScalaMutable[B](implicit c: Coercible[A, B]): scm.Seq[B] = underlying match {
    case w : s2j.MutableSeqWrapper[_] => coerce(w.underlying)
    case _ => coerce(new MutableListWrapper(underlying))
  }
}

private[collection] class RichSet[A](underlying: ju.Set[A]) {
  def asScala[B](implicit c: Coercible[A, B]): sc.Set[B] = underlying match {
    case w : s2j.SetWrapper[_] => coerce(w.underlying)
    case _ => coerce(new SetWrapper(underlying))
  }
  def asScalaMutable[B](implicit c: Coercible[A, B]): scm.Set[B] = underlying match {
    case w : s2j.MutableSetWrapper[_] => coerce(w.underlying)
    case _ => coerce(new MutableSetWrapper(underlying))
  }
}

private[collection] class RichMap[A, B](underlying: ju.Map[A, B]) {
  def asScala[C, D](implicit c1: Coercible[A, C], c2: Coercible[B, D]): sc.Map[C, D] = underlying match {
    case w : s2j.MapWrapper[_, _] => coerce2(w.underlying)
    case _ => coerce2(new MapWrapper(underlying))
  }
  def asScalaMutable[C, D](implicit c1: Coercible[A, C], c2: Coercible[B, D]): scm.Map[C, D] = underlying match {
    case w : s2j.MutableMapWrapper[_, _] => coerce2(w.underlying)
    case _ => coerce2(new MutableMapWrapper(underlying))
  }

  def foreach(f: ((A, B)) => Unit): Unit = {
    val g = (e: ju.Map.Entry[A, B]) => f(e.getKey, e.getValue)
    Helpers.foreach(underlying.entrySet.iterator, g)
  }
}

private[collection] class RichDictionary[A, B](underlying: ju.Dictionary[A, B]) {
  def asScala[C, D](implicit c1: Coercible[A, C], c2: Coercible[B, D]): scm.Map[C, D] = underlying match {
    case w : s2j.MutableMapDictionaryWrapper[_, _] => coerce2(w.underlying)
    case _ => coerce2(new DictionaryWrapper(underlying))
  }

  def foreach(f: ((A, B)) => Unit): Unit = {
    val g = (key: A) => f(key, underlying.get(key))
    Helpers.foreach(underlying.keys, g)
  }
}
