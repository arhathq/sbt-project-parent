package ua.dn.arhathq.sbt.parent

import sbt._
import Keys._
import net.virtualvoid.sbt.graph.DependencyGraphSettings.graphSettings

/**
  * Parent Project Sbt Config
  * Original implementation on http://engineering.sharethrough.com/blog/2015/09/23/capturing-common-config-with-an-sbt-parent-plugin/
  *
  * @author Alexander Kuleshov
  */
object SbtProjectParentPlugin extends AutoPlugin {
  object autoImport {
    val libraryVersions = settingKey[Map[Symbol, String]]("Common versions to be used for dependencies")
  }

  import autoImport._

  override def trigger: PluginTrigger = allRequirements

  val repositoryRoot = "http://localhost:8081/artifactory"

  val projectVersions = Map(

  )

  override def projectSettings: Seq[Setting[_]] = graphSettings ++ Seq(
    organization := "ua.dn.arhathq",

    scalaVersion := "2.12.8",

    scalacOptions ++= Seq(
      "-encoding", "UTF-8",
      "-deprecation",
      "-feature",
      "-language:higherKinds",
      "-Ywarn-dead-code",
      "-Xlint"
    ),

    // Update the SBT prompt with two features:
    // - color to better visually distinguish between SBT and scala prompts
    // - current project name to give better context when in repo with sub-projects
    shellPrompt := { s: State =>
      val c = scala.Console
      val blue = c.RESET + c.BLUE + c.BOLD
      val white = c.RESET + c.BOLD

      val projectName = Project.extract(s).currentProject.id

      blue + projectName + white + " \u00BB " + c.RESET
    },

    resolvers ++= Seq(
      "arhathq-releases" at repositoryRoot + "/maven-releases",
      "arhathq-snapshots" at repositoryRoot + "/maven-snapshots",
    ),

    libraryVersions := Map(
      'akka       -> "2.5.21",
      'akka_http  -> "10.1.7",
      'logback    -> "1.2.3",
      'slf4j      -> "1.7.25"
    ) ++ projectVersions,

    updateOptions := updateOptions.value.withCachedResolution(true),

    // remove any non-nop implementation of SLF4J during tests
    (dependencyClasspath in Test) ~= { cp =>
      cp.filterNot(_.data.name.contains("slf4j-log4j12"))
        .filterNot(_.data.name.contains("logback"))
    },

    credentials += Credentials("Artifactory Realm", "localhost", "admin", "admin123"),

    publishTo := {
      val root = repositoryRoot
      val layout = if (sbtPlugin.value) Resolver.ivyStylePatterns else Resolver.mavenStylePatterns
      val status = if (version.value.trim.endsWith("SNAPSHOT")) "snapshot" else "release"
      val repository = s"maven-${status}s"

      Some(Resolver.url(repository, new URL(s"$root/$repository/"))(layout))
    }
  )
}