# Comparison of Issue Tracking Tools

# Build & Run

## Pre-requisites
- Docker should be installed

## 1. YouTrack
- Build docker image `docker build -t youtrack ./youtrack`
- Run container `docker run -d --name youtrack-instance -p 8080:8080 youtrack:latest`
- Check starup logs `docker logs youtrack-instance` and copy the wizard_token
- Open a browser and go to `http://localhost:8080` and follow the wizard instructions
