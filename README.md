# assignment2

The goal of this project was to create a small Continuous Integration server. This server will receive a Webhook from Github, clone the project, build and run automated Unit tests. It will then update the status of the commit, to note whether it was a success or failure.

## Contributions

The parts of this program were split evenly and implemented mostly individually by all 4 members of the group. The basic code skeleton was decided upon together, including the main ContinousIntegrationServer file. We then setup a number of issues in Github, and divided them evenly so that one person worked on at least one of the bigger issues: compiling, testing, cloning, commit status and logging.

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

### Testing

To run automated tests, use the following command:

```
mvn test
```

## Dependency Management

To add a dependency, edit the *pom.xml* file and add the dependencies under *plugins*. 

Dependencies used in the project are:

- jUnit
- javax servlet
- eclipse jetty
- org.json
- commons-io
- github-api

## Description

### Cloning Git 

Once a webhook is received from Github, the corresponding repository is cloned in to the *test/* folder. This folder also checks-out the branch of which was regarded in the webhook. This way we may re-use the server for many different repositories.

### Compilation

Compilation of the repository code is done by doing a *cd* to the *test/* directory, followed by a:

```
mvn clean compile assembly:single
```

The output is then buffered and checked for a *completed status* text. If the parser finds that the compilation failed, an exception is thrown.

### Testing

The testing of the repository is done by doing a *cd* to the *test/* directory, followed by a:

```
mvn test
```

This will run the automated Unit tests, and a parses will check the output and decide whether all the tests completed successfully or not. If not, an exception is thrown.

### Updating commit status

Depending on the success or failure of the compilation and testing steps a commit status is sent to Github, through the Github API, as follows:

- *Compilation failure*: error
- *Testing failure*: failure
- *Compilation success and testing success*: success

### Logging builds

To log and keep track of the previous builds a JSON file is used, which stores the states of the builds with additional information.









