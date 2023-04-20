# Comparison of Issue Tracking Tools

# Build & Run

## Pre-requisites
- Docker should be installed

## 1. YouTrack
- Build docker image `docker build -t youtrack ./youtrack`
- Run container `docker run -d --name youtrack-instance -p 8080:8080 youtrack:latest`
- Check starup logs `docker logs youtrack-instance` and copy the wizard_token
- Open a browser and go to `http://localhost:8080` and follow the wizard instructions
- The REST API is accessible under `http://localhost:8080/rest` 

## 2. Bugzilla
- Build docker image `docker build -t bugzilla ./bugzilla`
- Run container `docker run -d --name bugzilla-instance -p 80:80 bugzilla:latest`
- Open browser and go to `http://localhost/bugzilla` and enter username `admin@test.com` and password `password`
- The REST API is accessible under `http://localhost/bugzilla/rest` URI

# Run Tests
The project contains executable targets that can be triggered for both Bugzilla and YouTrack which will automatically execute the test case scenarios. For the tests to work correctly, the system under test (YouTrack or Bugzilla) needs to be running with a Test Project created.
Once the project is opened in Gradle supported IDE such as IntelliJ, it should automatically build and provide the executable targets under Run Configuration
