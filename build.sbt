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

pomExtra in Global := {
  <url>https://github.com/caoilte/sbt-import-scala-files/</url>
    <licenses>
      <license>
        <name>Apache 2</name>
        <url>http://www.apache.org/licenses/LICENSE-2.0.txt</url>
      </license>
    </licenses>
    <scm>
      <connection>scm:git:github.com/caoilte/sbt-import-scala-files.git</connection>
      <developerConnection>scm:git:git@github.com:caoilte/sbt-import-scala-files.git</developerConnection>
      <url>github.com/caoilte/sbt-import-scala-files/</url>
    </scm>
    <developers>
      <developer>
        <id>caoilte</id>
        <name>Caoilte O'Connor</name>
        <url>http://caoilte.org</url>
      </developer>
    </developers>
}