#!/bin/sh
export EM_BASE_URI=http://localhost:8080
export IDAM_USER_BASE_URI=http://localhost:8081
export IDAM_BASE_URI=http://localhost:8082
#export GRADLE_OPTS="" #jenkins var we need

docker-compose down
docker-compose -f docker-compose.yml -f docker-compose-test.yml pull
docker-compose -f docker-compose.yml -f docker-compose-test.yml build
docker-compose up -d --build
#wget --retry-connrefused --tries=120 --waitretry=1 -O /dev/null http://localhost:8080/health
echo "Waiting 30s for the docker to warm up" && sleep 30s
./gradlew clean test --info
#docker-compose -f docker-compose.yml -f docker-compose-test.yml run -e GRADLE_OPTS tests
sensible-browser ./build/reports/tests/test/index.html
open ./build/reports/tests/test/index.html
xdg-open ./build/reports/tests/test/index.html
docker-compose down

