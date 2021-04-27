#!/bin/bash

javac functions/oneDimensionOptimisation/functions/*.java
javac functions/*.java
java functions.Main $@