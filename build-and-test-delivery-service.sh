#! /bin/bash -e

. ./set-env.sh

./gradlew :ftgo-delivery-service:test

./gradlew :ftgo-delivery-service:componentTest

