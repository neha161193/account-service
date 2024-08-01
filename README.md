# account-service

Account Service will help users to open their saving, current and fixed deposit account. One user can have multiple account like one saving and multiple FDs or one current and multiple FDs. This service will also help you to make payments to your account from other bank account like initial deposit.

## Pre-Requisite

This project needs postgresql database to run..
To setup postgres, please follow https://www.enterprisedb.com/downloads/postgres-postgresql-downloads

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:

```shell script
./mvnw compile quarkus:dev
```

## Code Coverage

Code coverage can be generated using below command:
```shell script
./mvnw verify
```
Coverage report will be generated to /account-service/target/jacoco-report/index.html , which can be viewed in browser

## Documentation

Endpoint documentaion can be seen in 2 ways
# 1. After running this project, it will be visible http://localhost:8080/q/swagger-ui/ 
# 2. Or copy the src/main/resources/swagger/account-service.yaml to https://editor.swagger.io/ to see..

Postman Collection can also be found at /src/main/resources/postman_collection/AccountService.postman_collection.json
