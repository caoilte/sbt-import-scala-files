# Import Scala Files [![Latest version](https://img.shields.io/badge/sbt_import_scala_files-1.0.0-green.svg)](https://mvnrepository.com/artifact/org.caoilte/sbt-import-scala-files_2.10_0.13/1.0.0/)

sbt-import-scala-files is a very small sbt plugin for importing Scala files into your build. This is most useful when you want to re-use Scala files from your [meta-build] (files under the project directory) in your proper build. 

## Rationale

This plugin was written to make it easier to write defaults plugins. A defaults plugin provides a sensible base configuration for other projects. It saves you the work of copy pasting lots and lots of sbt configuration between all of your projects. All your other projects only have to depend on one plugin and it takes care of everything else.

If you are writing a defaults plugin you may also find yourself wanting to make the same decisions that you make for your other projects about your defaults plugin as well. However, sbt provides no out of the box way to refer to meta-build configuration in the proper build. This plugin makes such inter-dependencies possible.

If this sounds like _turtles all the way down..._ it really is.

## Installation

Add the following to your `project/plugins.sbt` file:

```scala
addSbtPlugin("org.caoilte" % "sbt-import-scala-files" % "1.0.0")
```

The plugin is now enabled, but will not do anything until you define a `Seq[File]` to be imported with the `filesToImport` setting. It will then copy those files into your build's managed source directory before every compilation phase using the [source generation task][generating-files].

# Licence

[Apache 2.0][LICENCE]

## Use Cases

### Importing a meta-build file into your proper build

Imagine a library that depends on a particular library within Akka. Projects which depend on that project will pickup that dependency transitively for free, but if they want to depend on the same version of a different Akka library (all Akka libraries are released together) they cannot do so without manual inspection. By including the version of Akka a project depends on within its main Jar that Jar can be depended on within another projects meta-build and the Akka version used as the version there.  

Consider an example from this project's sbt tests [here][sbt-test-import-from-meta-build].

It has a file called [project/MetaBuildConstants.scala][from-meta-build-constants-file] that can be referred to from other Scala/sbt files in the meta-build but is un-reachable from the proper-build. We make it usable in the proper build by adding the following to our [build.sbt][from-meta-build-build-file].

```scala
filesToImport := Seq(file("project/MetaBuildConstants.scala"))
```

We are now able to use code from this Scala file in [a proper build Scala file][from-meta-build-main-file].

### Adding an sbt plugin to your sbt plugin project and to projects that depend on your project

The previous use case showed how to import a Scala file from the meta-build into the proper build. If you want to import sbt settings from your meta-build (eg plugin installations) into your proper build it takes a little more work.

One way is to create a Scala file in your meta-meta-build (ie the `project/project` directory) and import that into the meta-build and the proper build. Consider the example in this plugin's sbt tests [here][sbt-test-import-from-meta-build-and-meta-meta-build]. We want to write a defaults plugin that both adds [scalafmt] as a dependency (so that it is picked up transitively by projects which depend on our defaults plugin) and we want to run scalafmt on our defaults plugin as well.
 
 To do this we create a [project/project/Library.scala][from-meta-meta-build-library-file] file which defines the scalafmt `ModuleId` and then we,
  - copy that and install as a plugin in our meta-build in [project/plugins.sbt][from-meta-meta-build-plugins-file]
  - copy that and add as a dependency in our proper build in [build.sbt][from-meta-meta-build-build-file]



[meta-build]: http://www.scala-sbt.org/0.13/docs/Organizing-Build.html "Organizing the build"
[generating-files]: http://www.scala-sbt.org/0.13/docs/Howto-Generating-Files.html "Generating files"
[LICENCE]: https://github.com/caoilte/sbt-import-scala-files/blob/master/LICENCE "Licence"
[sbt-test-import-from-meta-build]: https://github.com/caoilte/sbt-import-scala-files/tree/master/src/sbt-test/sbt-import-scala-files/from-meta-build "sbt test import from meta-build"
[from-meta-build-constants-file]: https://github.com/caoilte/sbt-import-scala-files/blob/master/src/sbt-test/sbt-import-scala-files/from-meta-build/project/MetaBuildConstants.scala "meta-build constants"
[from-meta-build-build-file]: https://github.com/caoilte/sbt-import-scala-files/blob/master/src/sbt-test/sbt-import-scala-files/from-meta-build/build.sbt "From meta-build build.sbt"
[from-meta-build-main-file]: https://github.com/caoilte/sbt-import-scala-files/blob/master/src/sbt-test/sbt-import-scala-files/from-meta-build/src/main/scala/Main.scala#L6 "From meta-build Main.scala"
[sbt-test-import-from-meta-build-and-meta-meta-build]: https://github.com/caoilte/sbt-import-scala-files/tree/master/src/sbt-test/sbt-import-scala-files/from-meta-build-and-meta-meta-build "sbt test import from meta-build and meta-meta-build"
[from-meta-meta-build-library-file]: https://github.com/caoilte/sbt-import-scala-files/blob/master/src/sbt-test/sbt-import-scala-files/from-meta-build-and-meta-meta-build/project/project/Library.scala "From meta-meta-build library file"
[from-meta-meta-build-plugins-file]: https://github.com/caoilte/sbt-import-scala-files/blob/master/src/sbt-test/sbt-import-scala-files/from-meta-build-and-meta-meta-build/project/plugins.sbt "From meta-meta-build plugins file"
[from-meta-meta-build-build-file]: https://github.com/caoilte/sbt-import-scala-files/blob/master/src/sbt-test/sbt-import-scala-files/from-meta-build-and-meta-meta-build/build.sbt "From meta-meta-build build file"
[scalafmt]: https://olafurpg.github.io/scalafmt/ "Scalafmt"