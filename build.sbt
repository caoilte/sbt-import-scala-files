sbtPlugin := true

name := "sbt-import-scala-files"

organization := "org.caoilte"

scalaVersion := "2.10.6"

libraryDependencies += "com.lihaoyi" %% "fansi" % "0.2.3"

ScriptedPlugin.scriptedSettings

scriptedLaunchOpts := { scriptedLaunchOpts.value ++
  Seq("-Xmx1024M", "-Dplugin.version=" + version.value)
}

scriptedBufferLog := false