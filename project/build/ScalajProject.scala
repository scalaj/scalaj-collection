import sbt._
 
class ScalajProject(info: ProjectInfo) extends DefaultProject(info) with posterous.Publish {
  override def compileOptions = Seq(Deprecation, Unchecked)

  // Dependencies
  // val scalacheck = "org.scala-tools.testing" %% "scalacheck" % "1.7-SNAPSHOT"
  // val scalatest = "org.scalatest" % "scalatest" % "1.0.1-2-for-scala-2.8.0.Beta1-RC1-with-test-interfaces-0.1-SNAPSHOT" % "test"

  // Repositories
  // val scalaToolsSnapshots = "Scala-Tools Maven2 Snapshots Repository" at "http://scala-tools.org/repo-snapshots"
}
