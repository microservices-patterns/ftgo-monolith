#! /bin/bash -e

. ./set-env.sh

./gradlew testClasses assemble

docker-compose down --remove-orphans -v

./gradlew mySqlOnlyComposeUp

./gradlew -x :ftgo-end-to-end-tests:test -x :ftgo-delivery-service:componentTest $* build

docker-compose down --remove-orphans -v

./build-and-test-delivery-service.sh

docker-compose down --remove-orphans -v

./run-end-to-end-tests-microservices.sh


