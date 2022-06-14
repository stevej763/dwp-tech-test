# DWP Java Software Engineer Code Test

## Tasks

1. Call the city/{city}/users endpoint to list all users living in London, and return the results
2. Call the /users endpoint. Filter the results to return all users living within 50 miles of London. For the purpose of
   the exercise the centre of London is defined by the following Lat/Long: 51.5074° N, 0.1277° W.

## Guide

### Run the code

The simplest way to see the working application is to run the `mvn spring-boot:run` command from the repository root. This will run a simple demo to log out the results of the tasks. 

Alternatively, run `mvn install`, then `java -jar target/dwp-techtest-heo-1.0.jar` from the repository root for the same effect.

### Run the tests

There is also a full test suite, I began with some integration tests which you can find here:

[Integration test for test #1](src/test/java/integration/UsersInCityIntegrationTest.java)

[Integration test for task #2](src/test/java/integration/UsersInRadiusOfCityIntegrationTest.java)

All the code was written following Test-Driven-Developement, each class that contains any logic more complex than a getter has been thoroughly unit tested.

### Libraries

For the second task I depend on the [Geographiclib](https://geographiclib.sourceforge.io/html/java/net/sf/geographiclib/Geodesic.html) library in order to calculate the distance between two decimal coordinates.

I made use of [Spring Boot](https://spring.io/projects/spring-boot), mainly for the `RestTemplate` client to make HTTP calls to the RESTful API provided. (I excluded Tomcat in the POM file to avoid the embedded webserver running on application startup)

I use the [Jackson](https://github.com/FasterXML/jackson) `ObjectMapper` to map the JSON values from the API into the `User` domain object.

[Apache Commons](https://commons.apache.org/) for the reflection utilities.

[Junit](https://junit.org/junit4/), [Hamcrest](http://hamcrest.org/) and [Mockito](https://site.mockito.org/) are used for testing, mocking and stubbing.

