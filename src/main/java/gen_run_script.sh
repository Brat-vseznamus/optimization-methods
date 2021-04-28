#!/bin/bash

javac methods/dimensional/one/*.java
javac methods/dimensional/poly/*.java
javac methods/*.java
java -Xmx1g methods.Main $@