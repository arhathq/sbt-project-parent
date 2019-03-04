unmanagedSourceDirectories in Compile += baseDirectory.value.getParentFile / "src" / "main" / "scala"

addSbtPlugin("com.timushev.sbt"  %  "sbt-updates"           % "0.4.0")
addSbtPlugin("com.github.gseitz" %  "sbt-release"           % "1.0.11")
addSbtPlugin("org.scalastyle"    %% "scalastyle-sbt-plugin" % "1.0.0")
addSbtPlugin("net.virtual-void"  %  "sbt-dependency-graph"  % "0.9.2")
