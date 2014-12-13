name := """predictor"""

version := "1.0-SNAPSHOT"

playJavaSettings

ebeanEnabled := false

libraryDependencies ++= Seq(
    javaCore,
    javaJpa,
    "org.springframework" % "spring-context" % "4.1.3.RELEASE",
    "javax.inject" % "javax.inject" % "1",
    "org.springframework.data" % "spring-data-jpa" % "1.7.1.RELEASE",
    "org.springframework" % "spring-expression" % "4.1.3.RELEASE",
    "org.hibernate" % "hibernate-entitymanager" % "4.3.7.Final",
    "postgresql" % "postgresql" % "9.1-901.jdbc4",
    "org.mockito" % "mockito-core" % "1.9.5" % "test"
)