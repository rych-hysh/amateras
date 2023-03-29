#!/bin/bash
. ~/.nvm/nvm.sh
echo ""
echo "-----------------"
echo "start building..."
echo "-----------------"
echo ""
./gradlew build
echo ""
echo "-----------------"
echo "build complete."
echo "starting app..."
echo "-----------------"
echo ""
java -jar build/libs/*.jar
echo ""
echo "-----------------"
echo "app started."
echo "-----------------"
echo ""