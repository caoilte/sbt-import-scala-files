# Import Scala Files [![Latest version](https://img.shields.io/badge/sbt_import_scala_files-1.0-green.svg)](https://mvnrepository.com/artifact/org.caoilte/sbt-import-scala-files_2.10/1.0)

sbt-import-scala-files is a very small sbt plugin for importing Scala files into your build. This is most useful when you want to re-use Scala files from your [meta-build] (files under the project directory) in your proper build. 

## Rationale

This plugin was written to make it easier to write defaults plugins. A defaults plugin provides a sensible base configuration for other projects. It saves you the work of copy pasting lots and lots of sbt configuration between all of your projects. All your other projects only have to depend on one plugin and it takes care of everything else.

If you are writing a defaults plugin you may also find yourself wanting to make the same decisions that you make for your other projects about your defaults plugin as well. However, sbt provides no out of the box way to refer to meta-build configuration in the proper build. This plugin makes such inter-dependencies possible.

## Installation

Add the following to your `project/plugins.sbt` file:

```scala
addSbtPlugin("org.caoilte" % "sbt-import-scala-files" % "1.0.0")
```

The plugin is now enabled, but will not do anything until you define a `Seq[File]` to be imported with the `filesToImport` setting. It will then copy those files into your build's managed source directory before every compilation phase using the [source generation task][generating-files].

## Use Cases

### Importing a meta-build file into your proper build

If you have a file called `project/MetaBuildConstants.scala` you can import it into your proper build classpath by adding the following to your `build.sbt` file:

```scala
filesToImport := Seq(file("project/MetaBuildConstants.scala"))
```

When you run the `compile` task it will be copied into your build's managed source directory eg `target/scala-2.12/src_managed/Library.scala` before compilation. This means you can reliably depend on the code in your managed source files.

You can see an example of this in use in this plugin's sbt tests [here][sbt-test-simple-meta-build].

Your meta-build is an sbt project and this plugin is most useful when your proper build is also an sbt project and it wants to use the same code. For example a `project/ScalacOptions.scala` file that contains scala compiler settings that you want to use in the meta-build and in the proper build.

It is also useful for other projects, though. Imagine a library that depends on a particular library within Akka. Projects which depend on that project will pickup that dependency transitively for free, but if they want to depend on the same version of a different Akka library (all Akka libraries are released together) they cannot do so without manual inspection. By including the version of Akka a project depends on within its main Jar that Jar can be depended on within another projects meta-build and the Akka version used as the version there.  

### Adding an sbt plugin to your sbt plugin project and to projects that depend on your project

The previous example showed how to import a Scala file from the meta-build into the proper build. If you want to import sbt settings from your meta-build (eg plugin installations) into your proper build it takes a little more work.

One way is to create a Scala file in your meta-meta-build (ie the `project/project` directory) and import that into the meta-build and the proper build. Consider the example in this plugin's sbt tests [here][sbt-test-import-from-meta-build-and-meta-meta-build]. We want to write a defaults plugin that both adds [scalafmt] as a dependency (so that it is picked up transitively by projects which depend on our defaults plugin) and we want to run scalafmt on our defaults plugin as well.
 
 To do this we create a `project/project/Library.scala` file which defines the scalafmt `ModuleId` and then we,
  - copy that and install as a plugin in our meta-build in `project/plugins.sbt`
  - copy that and add as a dependency in our proper build in `build.sbt`



[meta-build]: http://www.scala-sbt.org/0.13/docs/Organizing-Build.html "Organizing the build"
[generating-files]: http://www.scala-sbt.org/0.13/docs/Howto-Generating-Files.html "Generating files"
[sbt-test-simple-meta-build]: "sbt test import from meta=build"
[sbt-test-import-from-meta-build-and-meta-meta-build]: "sbt test import from meta-build and meta-meta-build"
[scalafmt]: https://olafurpg.github.io/scalafmt/ "Scalafmt"