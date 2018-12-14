#! /bin/bash -e

. ./set-env.sh

./gradlew assemble

docker-compose build

. ./set-env.sh

docker-compose down -v
docker-compose up -d --build mysql

./gradlew waitForMySql

docker-compose up -d

./show-swagger-ui-urls.sh
