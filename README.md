# Demo

This is base template project based on spring boot

## Running the application in CLI
```sh
mvn spring-boot:run
```

To Stop the running process in _windows_,

```sh
netstat -ano | find "8080"
```
it shows the ```PID```

```sh
taskkill /f /pid {PID}
```

## Accessing the endpoint

The endpoint is behind the basic spring security ```suriya``` and password as ```secret```

```sh
curl -u suriya http://localhost:8080/demo/start
```

## Accessing actuator endpoints

```sh
curl http://localhost:9001/info
```

_Management_ port is set to ```9001```

## Build
This profile ```demo``` spits out ```jar```
```sh
mvn package -Pdemo
```
This  profile ```exttomcat``` spits out ```war```
```sh
mvn package -Pexttomcat
```

###########


# Prerequisite

These are the reqired artifacts to run the application,

1. IDE
2. Lombok
3. Java 1.8
4. Maven
5. Docker
6. Web browser

## Assumptions

### Direct input
1. Has to be 3 split with space seperator
2. Has to have "is" in the middle
3. Galatic string before is
4. Roman string after is

### Indirect input
1. Ends with credits
2. Credit value is before the text "credits"
3. Value is int
4. Separator should be space

### Direct question
1. Should start with "how much is"
2. End with "?"
3. Has only galactic string

### Indirect question
1. Should start with "how many Credits is"
2. Ends with "?"
3. Has one or many galactic string and one metal

### Irrevelant question
1. Can be anything thing that it irrevelant to the about assumptions 





