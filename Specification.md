# Demo Specification

This is based on spring boot

## Auto configuration

### spring-boot-starter-web
Provides
* Standalone appliction
* Embedded Tomcat, Jetty or can be deployed as war
* Provides healthcheck, metrics

### spring-boot-starter-test

### spring-boot-starter-actuator
Provides enpoints,
* info, metrics, health, dump, env, loggers, autoconfig, mappings(requestmappings), shutdown

__Note__ This works only with the embedded tomcat

### spring-boot-starter-hateoas

This provide more information apart form what is being asked in the api call

### spring-boot-starter-security

### spring-security-test

### spring-boot-starter-tomcat

This has been added because the _embedded tomcat_ has been disable in the _spring-boot-starter-web_

Add tomcat only when i want to run tomcat directly

### spring-boot-devtools
Provides,
* Automatic restart/refresh on code changes
* Remote debugging like when the appication is deployed in PCF or in a docker container

### springfox-swagger2
Provides,
* Documentation for the REST endpoints

### springfox-swagger-ui
Swagger UI helps to see the Swagger UI in the web,
http://localhost:8080/demo/swagger-ui.html

### tomcat7-maven-plugin


## Plugins

### spring-boot-maven-plugin
This provides 
* to run ```mvn spring-boot:run```
* to package ```war``` and ```jar```

