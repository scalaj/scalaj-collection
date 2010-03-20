package scalaj
package collection
package s2j

import java.{lang => jl, util => ju}
import scala.{collection => sc}
import scala.collection.{immutable => sci, mutable => scm}

private[collection] class RichIterator[A](underlying: sc.Iterator[A]) {
  def asJava: ju.Iterator[A] = underlying match {
    case w : j2s.IteratorWrapper[_] => w.underlying.asInstanceOf[ju.Iterator[A]]
    case _ => new IteratorWrapper(underlying)
  }
  def asJavaEnumeration: ju.Enumeration[A] = underlying match {
    case w : j2s.EnumerationWrapper[_] => w.underlying.asInstanceOf[ju.Enumeration[A]]
    case _ => new IteratorWrapper(underlying)
  }
}

private[collection] class RichIterable[A](underlying: sc.Iterable[A]) {
  def asJava: jl.Iterable[A] = underlying match {
    case w : j2s.IterableWrapper[_] => w.underlying.asInstanceOf[jl.Iterable[A]]
    case _ => new IterableWrapper(underlying)
  }
}

private[collection] class RichSeq[A](underlying: sc.Seq[A]) {
  def asJava: ju.List[A] = underlying match {
    case w : j2s.ListWrapper[_] => w.underlying.asInstanceOf[ju.List[A]]
    case _ => new SeqWrapper(underlying)
  }
}

private[collection] class RichMutableSeq[A](underlying: scm.Seq[A]) {
  def asJava: ju.List[A] = underlying match {
    case w : j2s.MutableListWrapper[_] => w.underlying
    case _ => new MutableSeqWrapper(underlying)
  }
}

private[collection] class RichBuffer[A](underlying: scm.Buffer[A]) {
  def asJava: ju.List[A] = new BufferWrapper(underlying)
}

private[collection] class RichSet[A](underlying: sc.Set[A]) {
  def asJava: ju.Set[A] = underlying match {
    case w : j2s.SetWrapper[_] => w.underlying
    case _ => new SetWrapper(underlying)
  }
}

private[collection] class RichMutableSet[A](underlying: scm.Set[A]) {
  def asJava: ju.Set[A] = underlying match {
    case w : j2s.MutableSetWrapper[_] => w.underlying
    case _ => new MutableSetWrapper(underlying)
  }
}

private[collection] class RichMap[A, B](underlying: sc.Map[A, B]) {
  def asJava: ju.Map[A, B] = underlying match {
    case w : j2s.MapWrapper[_, _] => w.underlying.asInstanceOf[ju.Map[A, B]]
    case _ => new MapWrapper(underlying)
  }
}

private[collection] class RichMutableMap[A, B](underlying: scm.Map[A, B]) {
  def asJava: ju.Map[A, B] = underlying match {
    case w : j2s.MutableMapWrapper[_, _] => w.underlying
    case _ => new MutableMapWrapper(underlying)
  }
}
