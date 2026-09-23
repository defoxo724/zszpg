#!/bin/bash

set -e

container_name="zszpg-mysql"

if docker container inspect "$container_name" >/dev/null 2>&1; then
  echo "Kontener $container_name już istnieje. Zatrzymaj go poleceniem: docker stop $container_name"
  exit 1
fi

cleanup() {
  docker stop "$container_name" >/dev/null 2>&1 || true
}

trap cleanup EXIT HUP INT TERM

docker run --rm --name "$container_name" \
  -e MYSQL_ROOT_PASSWORD=root123 \
  -e MYSQL_DATABASE=zszpg \
  -p 3306:3306 \
  -v zszpg_mysql_data:/var/lib/mysql \
  mysql:8.4 &

wait $!
