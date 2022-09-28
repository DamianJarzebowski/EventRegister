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
- I am Implements here new knowledge and test her.

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
What are the project requirements/dependencies? Where are they listed? A requirements.txt or a Pipfile.lock file perhaps? Where is it located?

Proceed to describe how to install / setup one's local environment / get started with the project.

## Usage
You can test application in swagger at your local machine.

http://localhost:8080/swagger-ui.html#/

## Project Status
Project is: _in progress_.

## Room for Improvement
Room for improvement:
- Api is clone database, have to change it.
- Improvement test code coverage.

To do:
- Operations based at transactional.
- Test in Cucumber.


