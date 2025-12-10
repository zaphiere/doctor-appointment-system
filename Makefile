# Makefile for Doctor Appointment System

.PHONY: install up down

# Install dependencies and build jar
install:
	mvn clean install -DskipTests
	docker-compose up --build

# Start services in detached mode
up:
	docker-compose up -d

# Stop services
down:
	docker-compose down
