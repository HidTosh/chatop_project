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
5 - Create tables on DB rental
- with spring : enable in application.properties line : `#spring.jpa.hibernate.ddl-auto=update`

OR 
- with mysql `mysql -u <user-name-db> -p rental < /PATH TO REPO/chatop_project/src/main/resources/static/schema.sql`


6 - Create RSA Keys and store them in `/src/main/resources/certs` (You should install openSSL if not installed)
```
# create rsa key pair (private key)
openssl genrsa -out keypair.pem 2048
# extract public key
openssl rsa -in keypair.pem -pubout -out public.pem
# create private key in PKCS#8 format (pkcs needed is needed) 
openssl pkcs8 -topk8 -inform PEM -outform PEM -nocrypt -in keypair.pem -out private.pem

```
7 - Chose type storage files, local or cloud aws S3 in app.properties
```
#change strategy of storage, user S3 bucket key (aws-s3), user local server apache2 (local-apache)
type.store.file=local-apache
```
- If storage S3 [create public bucket](https://repost.aws/knowledge-center/read-access-objects-s3-bucket) and [create key access](https://docs.aws.amazon.com/IAM/latest/UserGuide/id_root-user.html) then set in app.properties
```
## Default S3 configuration to store file image in public bucket
access.key.id=AKIASULWSCZ2FNOVF7KJ
access.key.secret=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
s3.region.name=eu-west-3
s3.bucket.name=chatop-rentals
```
- If storage local, need to install apache `sudo apt update` and `sudo apt install apache2` then change owner
of for the default storage folder exp :`sudo chown -R <user>.<user> /var/www/html` after that set in app.properties

```
folder.apache2.storage=/var/www/html/
localhost.url=http://localhost/
```


8 - Inside folder project exc  : 

    -  `mvn dependency:tree` # get dependency 
    -  `mvn spring-boot:run` # start project 


After mvn spring-boot:run the api will start on http://localhost:3001. A Swagger documentation is also available at http://localhost:3001/swagger-ui/index.html

# Start the whole project (back and front) : 

1 - After start of the api parts above, clone front side : https://github.com/HidTosh/Developpez-le-back-end-en-utilisant-Java-et-Spring

2 - Check first part on this repo (Readme) to install project (Start the project)

3 - After the start api and front you can visit : http://localhost:4200/
    
- You can create new user with button register (logged auto after new creation)
- You can also create rentals, update, show and send messages and login with other created users









