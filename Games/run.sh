#!/bin/bash
# Compile all Java files into bin directory
javac --release 8 -d bin **/*.java
# Run the main class with proper classpath
java -cp bin Games.Core.Main