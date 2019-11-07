#! /bin/bash -e

. ./set-env.sh

./gradlew testClasses

docker-compose down --remove-orphans -v

./gradlew mySqlOnlyComposeUp

./gradlew -x :ftgo-end-to-end-tests:test -x :ftgo-delivery-service:componentTest $* build

docker-compose build

./gradlew $* integrationTest

./run-end-to-end-tests-monolith.sh


