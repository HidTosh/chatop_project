# chatop_project

This project was generated with [Java 17.0.7](https://www.oracle.com/java/technologies/javase/17-0-7-relnotes.html) and [Spring Boot 3.1.2](https://spring.io/blog/2023/07/20/spring-boot-3-1-2-available-now)

# Overview

This app is an API application programming interface (API or web API) that allows creating users, rentals and messages ...

# Start api on local: 
1 - Install java 17.0.7

2 - Install mysql and create user and database named `rental`

3 - Clone this project on your local

4 - Update file (application.properties) add user and password db
```
spring.datasource.username=<provide your user name db>
spring.datasource.password=<provide your user password db>
```

4 - Inside folder project exc  : 

    -  `mvn dependency:tree` # get dependency 
    -  `mvn spring-boot:run` # start project 


After mvn spring-boot:run the api will start on http://localhost:3001. A Swagger documentation is also available at http://localhost:3001/swagger-ui/index.html

# Start the whole project : 

1 - After start of the api parts above, clone front side : https://github.com/HidTosh/Developpez-le-back-end-en-utilisant-Java-et-Spring

2 - Check first part on this repo to install project (Start the project)

3 - After the start api and front you can visit : http://localhost:4200/
    
- You can create new user with button register (logged auto after new creation)
- You can also create rentals, update, show and send messages and login with other created users









