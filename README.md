# Gatling with Spring boot java example 

This project explains how to use [Gatling](https://gatling.io/) using the crud operations for http request.

## Run

### Database 

This project uses **mongodb** as the state. So run [the docker compose]() to have the **mongodb** and **mongo express** 
to start with.

### Application to test

You can run the following project using,
```
java -jar 
```
or in your IDE of your choice.

To test the CRUD you can navigate to,

```
http://localhost:8080/swagger-ui/index.html
```

### Actual perf testing using Gatling

Check the plugin in the pom for more info on configuring which Simulations to run.

```
./gradlew.bat gatling:test
```

By the preceding way, it'll be easier for you to configure and run using any CI/CD of your choice.

## Output

Once you run the **gatling:test** plugin, you can view the test results in the */build* folder.


# TODO

- Try to connect app with custom user instead of *root* & *pass*
- Pageable SORT isn't working