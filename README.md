# assignment2

## Build instructions

For building and package management we are using Maven.

https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html

To build a JAR file with dependencies use the following command:

```
mvn clean compile assembly:single
```

And to run it:

```
java -cp target/assignment2-1.0-SNAPSHOT-jar-with-dependencies.jar kth.soffgrupp.se.ContinuousIntegrationServer
```

## Dependency Management

Edit the *pom.xml* file and add the dependencies under *<plugins>*.




