# Calendar API
## About The Project
It's a Calendar API to control the time slot available for users. They can Candidates or Interviewers.


## Built With 
- ```Java 11```
- ```PostgreSQL 11``` 
- ```JUnit```
- ```Maven (wrapper)```
- ```Swagger```
- ```Docker Compose``` 
 

## Prerequisites
- Java 11
- Docker Compose 

## Installation
After clone the project, decide if you want to run it completely under docker or only the database.

##### Option 1 - Running completely under docker
 
1.1 - Go to the folder /calendar-api/calendar
```sh
   ./mvnw clean package
   ```

1.2 - Run the command below to build the image at the folder /calendar-api/calendar
```sh
   docker build -t tamanna-app:1.0.0 .
   ```
   
1.3 - Return to the main folder /calendar-api and run  
 ```sh
   docker-compose -f docker-compose.yml up -d core-db tamanna-app
   ```

1.4 - Now open a browser and navigate to the URL(http://localhost:8080/tamanna/swagger-ui.html).
It's the swagger-ui, where you can test the api.

##### Option 2 - Running only the database under the docker

2.1 - Run the command below at the folder /calendar-api
```sh
   docker-compose -f docker-compose.yml up -d core-db
   ```

2.2 - Go to the folder /calendar-api/calendar and run:
```sh
   ./mvnw clean package
   ```

2.3 - And Run
```sh
   ./mvnw spring-boot:run
   ```
2.5 - Open Swagger-ui to use the api
http://localhost:8080/tamanna/swagger-ui.html


## Usage

##### Using this API you can insert, update, delete and fetch the users. A User can be a Candidate or an Interviewer.  

- http://localhost:8080/tamanna/swagger-ui.html#/user-controller


##### After inserting the user, it's possible to manage the time slot for each user and get the information of in which timeslot it is possible to have an interview between one Candidate with an Interviewer. 

- http://localhost:8080/tamanna/swagger-ui.html#/available-time-slot-controller


## Future Improvements

- Add a good way to track logs. One good solution should be user Kibana. 
- Implement some Integrantion Tests to cover the entire workflow of the API.
- Push the image of the api to the Docker Hub.
- Add Security. 












