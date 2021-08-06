# openinghours

Representation of opening hours for a restaurant

## How to Test

The whole test suite can be performed via `$ make test`

## How to run in IntelliJ

* Import the project
* Run the class `Main.kt`

## Technology choice

### Java 11
This is one of the most widely used java versions.

### Gradle
I chose Gradle to build the project because is the main standard in the industry.
The configuration is writen in Kotlin to keep the same language as the model.

### Vertx
This is a list of the most popular frameworks in [Kotlin for server side](https://kotlinlang.org/docs/server-overview.html#frameworks-for-server-side-development-with-kotlin)

I chose [Vertx](https://vertx.io/) because by simply importing a library a web server can be created easily and clearly.I also prefer to have the initial configuration in an imperative way instead of declarative. 

## Persistence

I would persist the information in a collection from the opening times in any NoSQL DB, for example a very simple solution is under a [sorted set in redis](https://redislabs.com/ebook/part-1-getting-started/chapter-1-getting-to-know-redis/1-2-what-redis-data-structures-look-like/1-2-5-sorted-sets-in-redis/)

## TODO

* Add the possibility to generate a fatjar and run it via console
* Add Integration tests that start the server and send request, and test with mocking of the service