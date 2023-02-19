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
