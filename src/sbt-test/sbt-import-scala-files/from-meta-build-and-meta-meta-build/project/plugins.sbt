sys.props.get("plugin.version") match {
  case Some(x) => addSbtPlugin("org.caoilte" % "sbt-import-scala-files" % x)
  case _ => sys.error("""|The system property 'plugin.version' is not defined.
                         |Specify this property using the scriptedLaunchOpts -D.""".stripMargin)
}

importScalaFilesList := Seq(file("project/project/Library.scala"))

org.caoilte.Library.pluginDependenciesAsPluginSettings