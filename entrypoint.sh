#!/bin/bash

echo "start building..."
./gradlew build
echo "build complete."
echo "starting app..."
java -jar build/libs/*.jar
echo "app started."