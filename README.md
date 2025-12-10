Doctor Appointment Springboot REST API

Backend RESTful application made using springboot for setting up a doctor's appointment

Requirements
- Java 17
- git
- GNU Make
- Docker
- WSL 2

Java App version
- Java 17

Docker setup
- Clone copy your computer
  - git clone git@github.com:zaphiere/doctor-appointment-system.git
- On your WSL terminal run:
  - make install
  
- wait until setup is finished (check terminal for status)

----------------------------------------
Postman Testing

Postman URL: http://localhost:8080/api/
- Postman Collection file can be found in the folder "Postman Collection"
  - filename: Doctor Appointment System.postman_collection.json

Initial Admin Credentials(JWT Login):
- username: admin
- password: password1234
----------------------------------------
pgAdmin Access

(Note: It might take awhile to access pgAdmin UI after reunning the docker setup)
Url: http://localhost:5050/

- email: admin@doctordev.com
- password: admin1234

How to setup pgAdmin server:
- Add New Server
- Input the following
  General tab:
  - Name: DoctorAppointmentDB
  Connection Tab
  - Hostname: postgres
  - Username: postgres
  - password: nameless
  - Maintenance database: doctor_appointment
  
 - Press save
  


