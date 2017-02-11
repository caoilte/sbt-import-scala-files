package org.caoilte

import sbt._

object V {
  val scalafmt = "0.5.5"
}

object Library {
  val scalafmtPlugin = "com.geirsson" % "sbt-scalafmt" % V.scalafmt

  val pluginDependencies = Seq(
    scalafmtPlugin
  )
  val pluginDependenciesAsPluginSettings = pluginDependencies.map(addSbtPlugin)
}
