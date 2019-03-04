lazy val `sbt-project-parent` = project in file(".")

name := "sbt-project-parent"

sbtPlugin := true

addSbtPlugin("com.timushev.sbt"  %  "sbt-updates"           % "0.4.0")
addSbtPlugin("com.github.gseitz" %  "sbt-release"           % "1.0.11")
addSbtPlugin("org.scoverage"     %  "sbt-scoverage"         % "1.5.1")
addSbtPlugin("org.scalastyle"    %% "scalastyle-sbt-plugin" % "1.0.0")
addSbtPlugin("net.virtual-void"  %  "sbt-dependency-graph"  % "0.9.2")
