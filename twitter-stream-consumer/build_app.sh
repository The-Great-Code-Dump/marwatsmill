#!/usr/bin/env bash
eval "$(./secret/secrets.sh)"
./gradlew build
docker build -f Dockerfile -t test-consumer:dev .
docker-compose up -d