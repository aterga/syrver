// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at http://mozilla.org/MPL/2.0/.
//
// Copyright (c) 2011-2020 ETH Zurich.


// Import general settings from Silver, Silicon, Carbon, ViperServer
lazy val silver = project in file("silver")
lazy val server = project in file("viperserver")


// Publishing settings
ThisBuild / Test / publishArtifact := true
// Allows 'publishLocal' SBT command to include test artifacts in a dedicated JAR file
// (whose name is postfixed by 'test-source') and publish it in the local Ivy repository.
// This JAR file contains all classes and resources for testing and projects like Carbon
// and Silicon can rely on it to access the test suit implemented in Silver.

ThisBuild / Test / parallelExecution := false

lazy val syrver = (project in file("."))
  .dependsOn(silver % "compile->compile;test->test")
  .dependsOn(server % "compile->compile;test->test")
  .enablePlugins(JavaAppPackaging)
  .settings(
    // General settings
    name := "Syrver",
    organization := "viper",
    version := "1.1-SNAPSHOT",

    // Compilation settings
    libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.5.22",
    libraryDependencies += "com.typesafe.akka" %% "akka-http-spray-json" % "10.1.8",
    libraryDependencies += "com.typesafe.akka" %% "akka-stream" % "2.5.22",
    libraryDependencies += "com.typesafe.akka" %% "akka-stream-testkit" % "2.5.22" % Test,
    libraryDependencies += "com.typesafe.akka" %% "akka-http-testkit" % "10.1.8" % Test,
    libraryDependencies += "org.eclipse.lsp4j" % "org.eclipse.lsp4j" % "0.8.1", // Java implementation of language server protocol

    // Run settings
    run / javaOptions += "-Xss128m",

    // Test settings
    fork := true,

    // Assembly settings
    assembly / assemblyJarName := "syrver.jar",                     // JAR filename
    assembly / mainClass := Some("viper.syrver.SyrverRunner"),      // Define JAR's entry point
    assembly / test := {},                                          // Prevent testing before packaging

      // Assembly settings
      assembly / assemblyJarName := "syrver.jar",                      // JAR filename
      assembly / mainClass := Some("viper.syrver.SyrverRunner"), // Define JAR's entry point
      assembly / test := {},                                          // Prevent testing before packaging
      assembly / assemblyMergeStrategy := {
          case "logback.xml" => MergeStrategy.first
          case PathList("viper", "silicon", "BuildInfo$.class") => MergeStrategy.first
          case x =>
              val fallbackStrategy = (assembly / assemblyMergeStrategy).value
              fallbackStrategy(x)
      }
  )
