@echo off
call mvn clean package
call docker build -t br.com.hfs/hefesto-j2ee8 .
call docker rm -f hefesto-j2ee8
call docker run -d -p 8080:8080 -p 4848:4848 --name hefesto-j2ee8 br.com.hfs/hefesto-j2ee8