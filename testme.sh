#!/bin/sh
export DM_GW_BASE_URI=http://localhost:8080
export DM_STORE_APP_BASE_URI=http://localhost:8083
export IDAM_USER_BASE_URI=http://localhost:8081
export IDAM_S2S_BASE_URI=http://localhost:8082
#export GRADLE_OPTS="" #jenkins var we need

docker-compose down
docker-compose -f docker-compose.yml -f docker-compose-test.yml pull
docker-compose -f docker-compose.yml -f docker-compose-test.yml build
docker-compose up -d --build

echo "Waiting for the docker to warm up"
#sleep 60s
wget --retry-connrefused --tries=120 --waitretry=1 -O /dev/null ${DM_STORE_APP_BASE_URI}/health

./idam.sh
./gradlew clean test --info
#docker-compose -f docker-compose.yml -f docker-compose-test.yml run -e GRADLE_OPTS document-management-store-integration-tests

sensible-browser ./build/reports/tests/test/index.html
open ./build/reports/tests/test/index.html
xdg-open ./build/reports/tests/test/index.html

docker-compose down

