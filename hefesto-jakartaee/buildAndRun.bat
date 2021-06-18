@echo off
call mvn clean package
call docker build -t br.com.hfs/hefesto-jakartaee8 .
call docker rm -f hefesto-jakartaee8
call docker run -d -p 9080:9080 -p 9443:9443 --name hefesto-jakartaee8 br.com.hfs/hefesto-jakartaee8