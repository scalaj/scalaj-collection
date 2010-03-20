package scalaj
package collection
package s2j

import java.{util => ju}
import scala.{collection => sc}
import scala.collection.{immutable => sci, mutable => scm}

class IteratorWrapper[A](val underlying: sc.Iterator[A]) extends ju.Iterator[A] with ju.Enumeration[A] {
  override def hasNext(): Boolean = underlying.hasNext
  override def next(): A = underlying.next
  override def remove(): Unit = throw new UnsupportedOperationException

  override def hasMoreElements(): Boolean = underlying.hasNext
  override def nextElement(): A = underlying.next
}

abstract class MutableIteratorWrapper[A](override val underlying: sc.Iterator[A]) extends IteratorWrapper(underlying) {
  protected[this] def remove(element: A): Unit

  private var prev: Option[A] = None

  override def remove(): Unit = {
    remove(prev.getOrElse(throw new IllegalStateException))
    prev = None
  }
  override def next(): A = {
    val rv = underlying.next
    prev = Some(rv)
    rv
  }
}

class IterableWrapper[A](val underlying: sc.Iterable[A]) extends ju.AbstractCollection[A] {
  override def size(): Int = underlying.size
  override def iterator(): ju.Iterator[A] = new IteratorWrapper(underlying.iterator)
}

class SeqWrapper[A](val underlying: sc.Seq[A]) extends ju.AbstractList[A] {
  override def size(): Int = underlying.size
  override def get(index: Int): A = underlying(index)
}

class MutableSeqWrapper[A](override val underlying: scm.Seq[A]) extends SeqWrapper(underlying) {
  override def set(index: Int, element: A): A = {
    val rv = underlying(index)
    underlying(index) = element
    rv
  }
}

class BufferWrapper[A](override val underlying: scm.Buffer[A]) extends MutableSeqWrapper(underlying) {
  override def remove(index: Int): A = underlying.remove(index)
  override def add(index: Int, element: A): Unit = underlying.insert(index, element)
}

class SetWrapper[A](val underlying: sc.Set[A]) extends ju.AbstractSet[A] {
  override def iterator(): ju.Iterator[A] = new IteratorWrapper(underlying.iterator)
  override def size(): Int = underlying.size
}

class MutableSetWrapper[A](override val underlying: scm.Set[A]) extends SetWrapper(underlying) {
  override def add(element: A): Boolean = {
    val s = underlying.size
    underlying += element
    s < underlying.size
  }
  override def iterator(): ju.Iterator[A] = new MutableIteratorWrapper(underlying.iterator) {
    override def remove(element: A): Unit = MutableSetWrapper.this.underlying.remove(element)
  }
}

object MapWrapper {
  class Entry[A, B](key: A, value: B) extends ju.Map.Entry[A, B] {
    override def getKey() = key
    override def getValue() = value
    override def setValue(newValue: B): B = throw new UnsupportedOperationException
    
    override def equals(that: Any): Boolean = that match {
      case that : ju.Map.Entry[_, _] => key == that.getKey && value == that.getValue
      case _ => false
    }
    override def hashCode(): Int =
      (if (key == null) 0 else key.hashCode) ^ (if (value == null) 0 else value.hashCode)
  }
}

class MapWrapper[A, B](val underlying: sc.Map[A, B]) extends ju.AbstractMap[A, B] {
  import MapWrapper.Entry

  override def entrySet(): ju.Set[ju.Map.Entry[A, B]] = new ju.AbstractSet[ju.Map.Entry[A, B]] {
    override def iterator(): ju.Iterator[ju.Map.Entry[A, B]] =
      new IteratorWrapper(underlying.iterator.map { case (k, v) => new Entry(k, v) })
    override def size(): Int = underlying.size
  }
}

class MutableMapWrapper[A, B](val underlying: scm.Map[A, B]) extends ju.AbstractMap[A, B] {
  import MapWrapper.Entry

  override def put(key: A, value: B): B = underlying.put(key, value).getOrElse(null.asInstanceOf[B])
  override def entrySet(): ju.Set[ju.Map.Entry[A, B]] =
    new ju.AbstractSet[ju.Map.Entry[A, B]] {
      override def iterator(): ju.Iterator[ju.Map.Entry[A, B]] =
        new MutableIteratorWrapper[ju.Map.Entry[A, B]](underlying.iterator.map { case (k, v) => new Entry(k, v) }) {
          override def remove(element: ju.Map.Entry[A, B]): Unit =
            MutableMapWrapper.this.underlying.remove(element.getKey)
        }
      override def size(): Int = underlying.size
    }
}
