package org.caoilte

import sbt._

object V {
  val scalafmt = "2.0.0"
}

object Library {
  val scalafmtPlugin = "org.scalameta" % "sbt-scalafmt" % V.scalafmt

  val pluginDependencies = Seq(
    scalafmtPlugin
  )
  val pluginDependenciesAsPluginSettings = pluginDependencies.map(addSbtPlugin)
}
