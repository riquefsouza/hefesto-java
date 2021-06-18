#!/bin/sh
if [ $(docker ps -a -f name=hefesto-jakartaee8 | grep -w hefesto-jakartaee8 | wc -l) -eq 1 ]; then
  docker rm -f hefesto-jakartaee8
fi
mvn clean package && docker build -t br.com.hfs/hefesto-jakartaee8 .
docker run -d -p 9080:9080 -p 9443:9443 --name hefesto-jakartaee8 br.com.hfs/hefesto-jakartaee8
