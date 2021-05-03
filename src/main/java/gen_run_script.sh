#!/bin/bash

javac methods/dimensional/one/*.java
javac methods/dimensional/poly/*.java
javac methods/*.java
# java -Xmx4g methods.Main $@
java methods.Main $@