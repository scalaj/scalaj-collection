package scalaj
package collection
package j2s

import java.{lang => jl, util => ju}

object Helpers {
  def foreach[A](it: ju.Iterator[A], f: A => Unit): Unit =
    while (it.hasNext) f(it.next)
  def foreach[A](it: ju.Enumeration[A], f: A => Unit): Unit =
    while (it.hasMoreElements) f(it.nextElement)
}
