# Gatling with Spring boot java example 

This project explains how to use [Gatling](https://gatling.io/) using the crud operations for http request. 

- **ChainBuilder** - individual HTTP request
- **ScenarioBuilder** - build how users uses those HTTP request
- **SetUp** - this is where you actually run the simulation, items within will execute in parallel - can run only one setUp in the class
- **HttpProtocol** - base URL and configurations

Other items include,
- **Session** - a session can store variables that could be fed to the next **ChainBuilder**
- **Feeder**
  - could be a **Iterator** of **Map<String, String>** for dynamic inputs
  - could be a csv file where the data is fed from

## Pre-requisite

- [Java 21](https://openjdk.org/projects/jdk/21/)
- [Mongo DB](https://github.com/suriyaprakhash/docker-collection/tree/master/services/mongo)

## Run

### Database 

This project uses **mongodb** as the state. So run [the docker compose](https://github.com/suriyaprakhash/docker-collection/tree/master/services/mongo#mongo--mongo-express) to have the **mongodb** and **mongo express** 
to start with.

### Application to test

Build the jar,
```
.\gradlew jar
```

You can run the following project using,
```
java -jar gatling-java-sample.jar
```
or in your IDE of your choice.

To test the CRUD you can navigate to,

```
http://localhost:8080/swagger-ui/index.html
```

### Actual perf testing using Gatling

Check the plugin in the **build.gradle** for more info on configuring which Simulations to run.

```
./gradlew gatlingRun --simulation=com.suriyaprakhash.gatling_java_sample.simulations.InventorySimulation -DingestionType=rampup
```

here,
  - **simulation** - specifies the class to run
  - **ingestionType** - is a custom system property to select the **stepUp** in the *InventorySimulation* 

By the preceding way, it'll be easier for you to configure and run using any CI/CD of your choice.

## Output

Once you run the **gatlingRun** plugin, you can view the test results in the */build* folder.

# Things to note

- Add the dependency needed by gatling as ***gatlingRuntimeOnly***
- Other transitive dependency which is part of the project won't be available during runtime
- Session var and the feed var can be accessed by using ***#{key}***
- When using windows using the **rampConcurrentUsers**, I got ```j.n.BindException: Address already in use: getsockopt```

## Ramp Up Injection

### rampUsers

User will make **one request** at a given second.

Users = Request

- rampUsers(10).during(Duration.ofSeconds(10) - will distribute 10 users across 10 sec at a constant pace
- rampUsers(50).during(Duration.ofSeconds(10) - will distribute 50 users across 10 sec at a constant pace
- rampUsers(50).during(Duration.ofSeconds(30) - will distribute 50 users across 30 sec at a constant pace

### rampUsersPerSec

User will make **one request** at a given second.

But the users won't be distributed like **rampUsers** instead they'll gradually increase
and stay in the session until the session is complete, resulting in even more requests. 
eg. 
- 1st second - 2 users making 2 request
- 2nd second - 4 users making 4 request
- 3rd second - 6 users making 6 request

- rampUsersPerSec(2).to(10).during(Duration.ofSeconds(10)) - Will ramp the user from 2 to 10 in 30 seconds. 
User created will stay until the session is over multiplying the requests.

### rampConcurrentUsers

The user that is injected into the system doesn't exit. So, it creates a spike of users at the end of the duration.

Its hard to correlate how many requests in this case. Experiment with your number and choose what works best for you.

# TODO

- Gatling Recordings
- Try to connect app with custom user instead of *root* & *pass*
- Pageable SORT isn't working as usual—but had a workaround for now. Fix it later.