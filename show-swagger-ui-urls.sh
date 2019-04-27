#! /bin/bash -e

./wait-for-services.sh

echo for API - open http://${DOCKER_HOST_IP?}:8081/swagger-ui.html
