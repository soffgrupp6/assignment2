#/bin/bash

mvn clean compile assembly:single
java -cp target/assignment2-1.0-SNAPSHOT-jar-with-dependencies.jar kth.soffgrupp.se.ContinuousIntegrationServer
