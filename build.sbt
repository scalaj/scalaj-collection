name := "scalaj-collection"

organization := "org.scalaj"

version := "1.3-SNAPSHOT"

crossScalaVersions := Seq("2.9.2", "2.10.2")

scalacOptions <++= scalaVersion map { (v: String) => 
  if (v.trim.startsWith("2.10"))
    Seq("-deprecation", "-feature", "-language:implicitConversions", "-language:higherKinds")
  else
    Seq("-deprecation")
}
