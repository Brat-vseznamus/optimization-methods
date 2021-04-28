#!/bin/bash

javac functions/onedim/*.java
javac functions/*.java
java -Xmx1g functions.Main $@