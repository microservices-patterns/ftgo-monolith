#!/bin/bash -e

docker-compose up -d --build $* mysql dynamodblocal

./initialize-dynamodb.sh


