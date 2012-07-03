package scalaj
package collection
package s2j

import java.{lang => jl, util => ju}
import scala.{collection => sc, math => sm}
import scala.collection.{immutable => sci, mutable => scm}
import Coercible.{coerce, coerce2}

private[collection] class RichOrdered[A](underlying: sm.Ordered[A]) {
  def asJava[B](implicit c: Coercible[A, B]): jl.Comparable[B] = coerce(underlying)
}

private[collection] class RichOrdering[A](underlying: sm.Ordering[A]) {
  def asJava[B](implicit c: Coercible[A, B]): ju.Comparator[B] = coerce(underlying)
}

private[collection] class RichIterator[A](underlying: sc.Iterator[A]) {
  def asJava[B](implicit c: Coercible[A, B]): ju.Iterator[B] = underlying match {
    case w : j2s.IteratorWrapper[_] => coerce(w.underlying.asInstanceOf[ju.Iterator[A]])
    case _ => coerce(new IteratorWrapper(underlying))
  }
  def asJavaEnumeration[B](implicit c: Coercible[A, B]): ju.Enumeration[B] = underlying match {
    case w : j2s.EnumerationWrapper[_] => coerce(w.underlying.asInstanceOf[ju.Enumeration[A]])
    case _ => coerce(new IteratorWrapper(underlying))
  }
}

private[collection] class RichIterable[A](underlying: sc.Iterable[A]) {
  def asJava[B](implicit c: Coercible[A, B]): jl.Iterable[B] = underlying match {
    case w : j2s.IterableWrapper[_] => coerce(w.underlying.asInstanceOf[jl.Iterable[A]])
    case _ => coerce(new IterableWrapper(underlying))
  }
}

private[collection] class RichSeq[A](underlying: sc.Seq[A]) {
  def asJava[B](implicit c: Coercible[A, B]): ju.List[B] = underlying match {
    case w : j2s.ListWrapper[_] => coerce(w.underlying.asInstanceOf[ju.List[A]])
    case ls: sci.LinearSeq[_] => coerce(new LinearSeqWrapper(ls))
    case _ => coerce(new SeqWrapper(underlying))
  }
}

private[collection] class RichMutableSeq[A](underlying: scm.Seq[A]) {
  def asJava[B](implicit c: Coercible[A, B]): ju.List[B] = underlying match {
    case w : j2s.MutableListWrapper[_] => coerce(w.underlying)
    case _ => coerce(new MutableSeqWrapper(underlying))
  }
}

private[collection] class RichBuffer[A](underlying: scm.Buffer[A]) {
  def asJava[B](implicit c: Coercible[A, B]): ju.List[B] = coerce(new BufferWrapper(underlying))
}

private[collection] class RichSet[A](underlying: sc.Set[A]) {
  def asJava[B](implicit c: Coercible[A, B]): ju.Set[B] = underlying match {
    case w : j2s.SetWrapper[_] => coerce(w.underlying)
    case _ => coerce(new SetWrapper(underlying))
  }
}

private[collection] class RichMutableSet[A](underlying: scm.Set[A]) {
  def asJava[B](implicit c: Coercible[A, B]): ju.Set[B] = underlying match {
    case w : j2s.MutableSetWrapper[_] => coerce(w.underlying)
    case _ => coerce(new MutableSetWrapper(underlying))
  }
}

private[collection] class RichMap[A, B](underlying: sc.Map[A, B]) {
  def asJava[C, D](implicit c1: Coercible[A, C], c2: Coercible[B, D]): ju.Map[C, D] = underlying match {
    case w : j2s.MapWrapper[_, _] => coerce2(w.underlying.asInstanceOf[ju.Map[A, B]])
    case _ => coerce2(new MapWrapper(underlying))
  }
}

private[collection] class RichMutableMap[A, B](underlying: scm.Map[A, B]) {
  def asJava[C, D](implicit c1: Coercible[A, C], c2: Coercible[B, D]): ju.Map[C, D] = underlying match {
    case w : j2s.MutableMapWrapper[_, _] => coerce2(w.underlying)
    case _ => coerce2(new MutableMapWrapper(underlying))
  }
  def asJavaDictionary[C, D](implicit c1: Coercible[A, C], c2: Coercible[B, D]): ju.Dictionary[C, D] = underlying match {
    case w : j2s.DictionaryWrapper[_, _] => coerce2(w.underlying.asInstanceOf[ju.Dictionary[A, B]])
    case _ => coerce2(new MutableMapDictionaryWrapper(underlying))
  }
}
