# Contacts List Backend

This is a test application with Spring Boot as Backend for simple Contacts list

## 1. Backend App

* JDK 1.8
* Spring Boot 2.1.0
* Spring Web 2.1.0
* Spring Boot JPA 2.1.0
* Spring Boot Beans Validation 2.1.0
* PostgreSQL 9.6 (Postgres is the core for [EDB Postgres](https://www.enterprisedb.com/))
* Apache Commons CSV 1.6
* Lombok 1.18
* Maven 3

## 2. Build Setup

```bash
# install dependencies
mvn install

# serve at localhost:8080
java -jar target/contacts-0.0.1-SNAPSHOT.jar

# build for production 
mvn package [-DskipTests]
```
