#!/bin/bash

docker run --rm --name zszpg-mysql -e MYSQL_ROOT_PASSWORD=root123 -e MYSQL_DATABASE=zszpg -p 3306:3306 -v zszpg_mysql_data:/var/lib/mysql mysql:8.4