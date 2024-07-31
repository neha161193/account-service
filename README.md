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




Endpoint documentaion can be seen after running this project at http://localhost:8080/q/swagger-ui/

Postman Collection can also be found at /src/main/resources/postman_collection/AccountService.postman_collection.json
