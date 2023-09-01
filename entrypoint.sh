#!/bin/bash
echo ""
echo "-----------------"
echo "start building..."
echo "-----------------"

if [ "$IS_BUILD_MODE" = "false" ]; then
 ./gradlew bootrun --args='--spring.profiles.active=dev'
  echo ""
  echo "-----------------"
  echo "bootrun complete."
  echo "-----------------"
  echo ""
else
  echo ""
  ./gradlew build
  echo ""
  echo "-----------------"
  echo "build complete."
  echo "starting app..."
  echo "-----------------"
  echo ""
  java -jar build/libs/*.jar --spring.profiles.active=dev
  echo ""
  echo "-----------------"
  echo "app started."
  echo "-----------------"
  echo ""
fi


