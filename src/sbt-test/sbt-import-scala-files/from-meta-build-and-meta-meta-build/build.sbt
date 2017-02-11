version := "0.1"

scalaVersion := "2.10.6"

filesToImport := Seq(file("project/project/Library.scala"))

sbtPlugin := true

lazy val root = (project in file(".")).
settings(
  addSbtPlugin(org.caoilte.Library.scalafmtPlugin)
)