inThisBuild(
  List(
    organization := "org.caoilte",
    homepage := Some(url("https://github.com/caoilte/sbt-import-scala-files/")),
    licenses := List("Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0")),
    developers := List(
      Developer(
        "caoilte",
        "Caoilte O'Connor",
        "caoilte@gmail.com",
        url("https://caoilte.org")
      )
    )
  )
)

sbtPlugin := true

name := "sbt-import-scala-files"

scalaVersion := "2.12.10"

libraryDependencies += "com.lihaoyi" %% "fansi" % "0.2.9"

enablePlugins(SbtPlugin)

scriptedLaunchOpts := {
  scriptedLaunchOpts.value ++
    Seq("-Xmx1024M", "-Dplugin.version=" + version.value)
}

scriptedBufferLog := false
