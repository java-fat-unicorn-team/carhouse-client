# carhouse-client [![Build Status](https://travis-ci.com/java-fat-unicorn-team/carhouse-client.svg?branch=master)](https://travis-ci.com/java-fat-unicorn-team/carhouse-client) [![codecov](https://codecov.io/gh/java-fat-unicorn-team/carhouse-client/branch/master/graph/badge.svg)](https://codecov.io/gh/java-fat-unicorn-team/carhouse-client)
## Main topic of project
It is a web application which provide opportunity to place an advertisement for the sale of a car or find an existent one with selected parameters.

## Technologies
Project is created with:
* Java 11
* Spring core 5.1.7
* Thymeleaf 3.0.11
* Git 2+
* Gradle 5.5+
* Tomcat 9+
* Apache 2.4+

## Build
```
gradle clean build
```

## Deploy
```
gradle tomcatRun
```
##### *to make the application work you need to [CONFIGURE APACHE SERVER](apache-config/apache_config.md)