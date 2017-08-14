#!/bin/bash

mvn clean
mvn assembly:assembly

java -jar target/2017-04-01-hw-14.jar

exit