#! /bin/bash -e

DOCKER_COMPOSE_PREFIX=$(echo ${PWD##*/} | sed -e 's/-//g')_

DOCKER_REPO=msapatterns

$PREFIX docker login -u ${DOCKER_USER_ID?} -p ${DOCKER_PASSWORD?}

IMAGES="ftgo-service dynamodblocal-init mysql"

cd dynamodblocal-init
$PREFIX ./build-docker.sh
$PREFIX docker tag test-dynamodblocal-init:latest ${DOCKER_COMPOSE_PREFIX?}dynamodblocal-init
cd ..

function tagAndPush() {
  LOCAL=$1
  REMOTE="$2"
  FULL_LOCAL=${DOCKER_COMPOSE_PREFIX?}$LOCAL
  FULL_REMOTE="$DOCKER_REPO/$REMOTE:latest"

  $PREFIX docker tag $FULL_LOCAL $FULL_REMOTE
  $PREFIX docker push $FULL_REMOTE
}

for image in $IMAGES ; do
    tagAndPush $image $image
done
