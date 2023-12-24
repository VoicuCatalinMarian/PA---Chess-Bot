# Example for Makefile for solutions in Java.

.PHONY: build run clean

build:
	javac Main.java

run:
	java Main

clean:
	rm -r *.class