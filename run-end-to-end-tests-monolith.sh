#! /bin/bash -e

./gradlew -P ftgoTestMode=monolith :ftgo-end-to-end-tests:cleanTest :ftgo-end-to-end-tests:test
