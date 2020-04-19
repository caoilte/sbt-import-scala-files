package org.caoilte.sbtimportscalafiles

import fansi.Str
import sbt.Keys._
import sbt._

object ImportScalaFilesPlugin extends AutoPlugin {

  override def requires = sbt.plugins.JvmPlugin

  override def trigger = allRequirements

  object autoImport {
    lazy val importScalaFilesList = settingKey[Seq[File]](
      "Scala files to be imported into this build's generated source directory by 'importScalaFiles' task"
    )
    lazy val importScalaFiles = taskKey[Seq[File]](
      "Import files defined in 'importScalaFilesList' setting to this build's generated source directory"
    )
  }
  import autoImport._

  override lazy val projectSettings = Seq(
    importScalaFilesList := Nil,
    importScalaFiles := {
      val sts = streams.value
      val logger = sts.log
      importScalaFilesList.value.map { metaProjectFileToImport =>
        val copiedFile: File = ImportScalaFiles(sourceManaged.value, metaProjectFileToImport)

        val baseDirOfLocalRootProject = (baseDirectory in LocalRootProject).value

        logger.info(
          ImportScalaFiles.copiedFileString(
            baseDirOfLocalRootProject = baseDirOfLocalRootProject,
            from = metaProjectFileToImport,
            to = copiedFile
          )
        )

        copiedFile
      }
    },
    sourceGenerators in Compile += importScalaFiles.taskValue
  )
}

object ImportScalaFiles {

  private def relativeBaseDir(absoluteBaseDir: File): String = {
    if (absoluteBaseDir.toPath.endsWith("project")) {
      "project"
    } else "."
  }

  private val relativeLocation: File => File => String = baseDirectory =>
    file => {
      val index = baseDirectory.getPath.length + 1
      file.getAbsolutePath.substring(index)
    }

  def copiedFileString(baseDirOfLocalRootProject: File, from: File, to: File): String = {
    val relativeToBaseDir = relativeLocation(baseDirOfLocalRootProject)

    val baseDirStr = relativeBaseDir(baseDirOfLocalRootProject)
    val fromStr = relativeToBaseDir(from)
    val toStr = relativeToBaseDir(to)

    import fansi.Color._
    (
      Cyan("ImportScalaFiles") ++
        Str(": In '") ++ Green(baseDirStr) ++
        Str("' copied '") ++ Green(fromStr) ++
        Str("' to '") ++ Green(toStr) ++ White("'")
    ).render
  }

  def apply(toDirectory: File, file: File): File = {
    val toFile = toDirectory / file.name
    IO.copyFile(file, toFile, preserveLastModified = true)
    toFile
  }
}
