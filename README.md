# openinghours

Representation of opening hours for a restaurant

## Preconditions

### [Brew](https://brew.sh/)

This is the most popular packet manager for OSX

## Setup

In case the environment is missing Java 11 or Gradle.

Run: `$ make install`

This will install the following dependencies:

### Java 11
This is one of the most widely used java versions. It is installed via the version manager [jabba](https://github.com/shyiko/jabba).

### Gradle
I chose Gradle to build the project because is the main standard in the industry. 
The configuration is writen in Kotlin to keep the same language as the model.

## How to Test

The whole test suite can be performed via `$ make test`

## TODO
Installation support for Linux or Windows