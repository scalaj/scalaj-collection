package scalaj
package collection
package j2s

import java.{lang => jl, util => ju}
import scala.{collection => sc}
import scala.collection.{immutable => sci, mutable => scm}

object Implicits extends Implicits
trait Implicits {
  // class Convertible1Wrapper[M[_], A](x: M[A]) {
  //   def asScala[N[_], B](implicit c0: Convertible0[A, B], c1: Convertible1[M, N]): N[B] = c1(x)
  // }

  // class CC1W[M2[_], M1[_], A](x: M2[M1[A]]) {
  //   def asScala[N2[_], N1[_], B](implicit c0: Convertible0[A, B], c1: Convertible1[M1, N1], c2: Convertible1[M2, N2]): N2[N1[B]] = c2(x)
  // }

  // class Convertible2Wrapper[M[_, _], A, B](x: M[A, B]) {
  //   def asScala[N[_, _], C, D](implicit c0a: Convertible0[A, C], c0b: Convertible0[B, D], c2: Convertible2[M, N]): N[C, D] = c2(x)
  // }

  // implicit def Convertible1Wrapper[M[_], A](x: M[A]): Convertible1Wrapper[M, A] = new Convertible1Wrapper[M, A](x)
  // implicit def Convertible2Wrapper[M[_, _], A, B](x: M[A, B]): Convertible2Wrapper[M, A, B] = new Convertible2Wrapper[M, A, B](x)
  // implicit def CC1W[M2[_], M1[_], A](x: M2[M1[A]]): CC1W[M2, M1, A] = new CC1W[M2, M1, A](x)
}

trait Convertible0[A, B] extends (A => B)

trait Biconvertible0[A, B] extends Convertible0[A, B] {
  def reverse: Biconvertible0[B, A]
}

trait Convertible1[M[_], N[_]] {
  def apply[A, B](x: M[A])(implicit convert: Convertible0[A, B]): N[B]
}

trait Convertible2[M[_, _], N[_, _]] {
  def apply[A, B, C, D](x: M[A, B])(implicit c1: Convertible0[A, C], c2: Convertible0[B, D]): N[C, D]
}

object Biconvertible0 extends LowPriorityConvertible0 {
  private[Biconvertible0] class PrimitiveBiconvertible0[A, B] extends Biconvertible0[A, B] { outer =>
    override def apply(x: A): B = x.asInstanceOf[B]
    override lazy val reverse: Biconvertible0[B, A] = new Biconvertible0[B, A] {
      override def apply(x: B): A = x.asInstanceOf[A]
      override def reverse: Biconvertible0[A, B] = outer
    }
  }

  implicit object Biconvertible0Boolean extends PrimitiveBiconvertible0[jl.Boolean, Boolean]
  implicit object Biconvertible0Char extends PrimitiveBiconvertible0[jl.Character, Char]
  implicit object Biconvertible0Byte extends PrimitiveBiconvertible0[jl.Byte, Byte]
  implicit object Biconvertible0Short extends PrimitiveBiconvertible0[jl.Short, Short]
  implicit object Biconvertible0Int extends PrimitiveBiconvertible0[jl.Integer, Int]
  implicit object Biconvertible0Long extends PrimitiveBiconvertible0[jl.Long, Long]
  implicit object Biconvertible0Float extends PrimitiveBiconvertible0[jl.Float, Float]
  implicit object Biconvertible0Double extends PrimitiveBiconvertible0[jl.Double, Double]
}

object Convertible0 extends LowPriorityConvertible0 {
  private[Convertible0] class Convertible0FromWrapper[M[_], N[_], A, B](f: M[A] => N[B]) extends Convertible0[M[A], N[B]] {
    override def apply(x: M[A]): N[B] = f(x)
  }
  
  implicit def Convertible0EnumerationIterator[A, B](implicit c: Convertible0[A, B]): Convertible0[ju.Enumeration[A], Iterator[B]] =
    new Convertible0FromWrapper[ju.Enumeration, Iterator, A, B](new EnumerationWrapper[A, B](_)(c))
  implicit def Convertible0IteratorIterator[A, B](implicit c: Convertible0[A, B]): Convertible0[ju.Iterator[A], Iterator[B]] =
    new Convertible0FromWrapper[ju.Iterator, Iterator, A, B](new IteratorWrapper[A, B](_)(c))
  implicit def Convertible0IterableIterator[A, B](implicit c: Convertible0[A, B]): Convertible0[jl.Iterable[A], Iterable[B]] =
    new Convertible0FromWrapper[ju.Iterable, Iterable, A, B](new IterableWrapper[A, B](_)(c))
  implicit def Convertible0CollectionIterator[A, B](implicit c: Convertible0[A, B]): Convertible0[ju.Collection[A], Iterable[B]] =
    new Convertible0FromWrapper[ju.Collection, Iterable, A, B](new CollectionWrapper[A, B](_)(c))
  implicit def Convertible0EnumerationIterator[A, B](implicit c: Convertible0[A, B]): Convertible0[ju.Enumeration[A], Iterator[B]] =
    new Convertible0FromWrapper[ju.Enumeration, Iterator, A, B](new EnumerationWrapper[A, B](_)(c))
  implicit def Convertible0EnumerationIterator[A, B](implicit c: Convertible0[A, B]): Convertible0[ju.Enumeration[A], Iterator[B]] =
    new Convertible0FromWrapper[ju.Enumeration, Iterator, A, B](new EnumerationWrapper[A, B](_)(c))
}

object Convertible1 {
  // implicit object Convertible1List extends Convertible1[java.util.List, Seq] {
  //   override def apply[A, B](x: ju.List[A])(implicit convert: Convertible0[A, B]): Seq[B] = new ListWrapper(x)
  // }
}

trait LowPriorityConvertible0 {
  implicit def Convertible0Self[A]: Convertible0[A, A] = new Convertible0[A, A] {
    override def apply(x: A): A = x
  }
}
