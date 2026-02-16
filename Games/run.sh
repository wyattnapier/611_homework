#!/bin/bash
# Compile all Java files into bin directory
javac -d bin **/*.java
# Run the main class with proper classpath
java -cp bin Games.Core.Main