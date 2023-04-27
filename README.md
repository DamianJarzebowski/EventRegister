# Event Register
> Academic project for test new skills and provide new know functional.

## Table of Contents
* [General Info](#general-information)
* [Technologies Used](#technologies-used)
* [Features](#features)
* [Setup](#setup)
* [Usage](#usage)
* [Project Status](#project-status)
* [Room for Improvement](#room-for-improvement)

## General Information
- Event Register is api, which main functional is register participants and save them on events.
- This project is making to help me get commercial experience in programing REST API.
- I Implement here new knowledge and test her.

## Technologies Used
- Java - version 17
- Spring Boot - version 2.7.0
- Liquibase
- Postgresql
- H2
- Lombok
- Swagger - version 2.9.2
- Rest Assured - version 4.3.3
- AssertJ
- JUnit5
- Docker
- SonarLint (https://sonarcloud.io/project/overview?id=DamianJarzebowski_EventRegister)

## Features
List the ready features here:

- Get, save and delete category of event.
- Get, save, update and delete participant.
- Get, save, update and delete events.
- Get, save, and delete event records.

Design patterns:
- Can make operations at state machine based events.

## Setup

#### Start with docker (Actually using only dev profile without access to h2-console)
1. Start script:
   build-and-restart.bat
2. Use this link in your browser, come to swagger and start testing

   http://localhost:8080/swagger-ui.html#

#### Start with IDE as dev

1. Set profile in configurations: SPRING_PROFILES_ACTIVE=dev
2. Run api
3. Use this link in your browser, come to swagger and start testing

    http://localhost:8080/swagger-ui.html#

4.Access to console is at this link 

http://localhost:8080/konsola-h2

- username: admin
- password:

#### Start with IDE as prod

1. Set profile in configurations: SPRING_PROFILES_ACTIVE=prod
2. Create container postgresql with command below

docker run --name some-postgres -p 5432:5432 -e POSTGRES_PASSWORD=mysecretpassword -d postgres

3. Run api
4. Use this link in your browser, come to swagger and start testing

    http://localhost:8080/swagger-ui.html#

5. If have you tool to connect with database use this:
- Connection type: PostgreSQL 
- Server name: localhost
- User name: postgres
- Password: mysecretpassword
- Port: 5432

## Usage
You can test application in swagger at your local machine.

http://localhost:8080/swagger-ui.html#/

## Project Status
Project is: _in progress_.

## Room for Improvement
Room for improvement:
- Improvement test code coverage.
- Container connection with server postgreSQL



