#!/bin/bash
mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Dspring.devtools.restart.enabled=true -Dspring.devtools.livereload.enabled=true" &
while inotifywait -r -e modify,create,delete src/; do
    echo "File changes detected, recompiling..."
    mvn compile
done