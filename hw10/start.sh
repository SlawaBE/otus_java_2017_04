#!/bin/bash

# Для подготовки БД можно использовать команду
# mysql -p < prepare_database.sql

mvn clean
mvn assembly:assembly

java -Xmx512m -Xms512m -jar target/2017-04-01-hw-10.jar

exit