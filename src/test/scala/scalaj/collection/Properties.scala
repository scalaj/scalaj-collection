// package scalaj
// package collection
// 
// import java.{lang => jl, util => ju}
// import org.scalatest.FunSuite
// import org.scalatest.prop.Checkers
// import org.scalacheck._
// import org.scalacheck.Arbitrary._
// import org.scalacheck.Prop._
// 
// class S2JSuite extends FunSuite with Checkers {
//   import Prop.forAll
// 
//   test("SeqWrapper") {
//     check { (a: List[String]) =>
//       val b = new s2j.SeqWrapper(a)
//       val index = Gen.choose(0, a.size - 1)
//       forAll(index)(i => a(i) == b.get(i))
//     }
// 
//     check { (a: List[String]) =>
//       val b = new s2j.SeqWrapper(a)
//       a.size == b.size
//     }
// 
//     check { (a: List[String]) =>
//       val b = new s2j.SeqWrapper(a)
//       val str = (Gen.choose(0, a.size - 1) map (a.apply _)) | Arbitrary.arbitrary[String]
//       forAll(str)(s => a.contains(s) ==> b.contains(s))
//     }
// 
//     check { (a: List[String]) =>
//       forAll(Gen.oneOf(a) | Arbitrary.arbitrary[List[String]]) { b =>
//         val c = new s2j.SeqWrapper(a)
//         val d = new s2j.SeqWrapper(b)
//         (a == b) ==> (c == d)
//         (a.hashCode == b.hashCode) ==> (c.hashCode == d.hashCode)
//       }
//     }
//   }
// }
// 
// object S2JSpecification extends Properties("scalaj.collection.s2j") {
//   property("hashCode") = forAll { (a: List[String], b: List[String]) =>
//     a.hashCode == b.hashCode ==> {
//       val c = new s2j.SeqWrapper(a)
//       val d = new s2j.SeqWrapper(b)
//       c.hashCode == d.hashCode
//     }
//   }
//   
//   property("indexOf") = forAll { (a: List[String], s: String) =>
//     val b = new s2j.SeqWrapper(a)
//     a.indexOf(s) == b.indexOf(s)
//   }
//   
//   property("isEmpty") = forAll { (a: List[String]) =>
//     val b = new s2j.SeqWrapper(a)
//     a.isEmpty == b.isEmpty
//   }
// }
