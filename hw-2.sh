#!/bin/bash

mvn clean
mvn assembly:assembly

java -Xmx512m -Xms512m -jar target/2017-04-01-hw-2.jar

exit
