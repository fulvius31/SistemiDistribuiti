#!/bin/bash
# this is a start script for init docker and all environment as mysql tables

sudo docker-compose build
sudo docker-compose up -d
sleep 10
mysql -h 10.123.123.200 -u root -pSistemiDistribuiti2017 -e "USE sistemidistribuiti; CREATE TABLE log(id VARCHAR(30), timestamp VARCHAR(60), msg VARCHAR(130));"
mysql -h 10.123.123.201 -u root -pSistemiDistribuiti2017 -e "USE sistemidistribuiti; CREATE TABLE log(id VARCHAR(30), timestamp VARCHAR(60), msg VARCHAR(130));"
mysql -h 10.123.123.202 -u root -pSistemiDistribuiti2017 -e "USE sistemidistribuiti; CREATE TABLE log(id VARCHAR(30), timestamp VARCHAR(60), msg VARCHAR(130));"
mysql -h 10.123.123.203 -u root -pSistemiDistribuiti2017 -e "USE sistemidistribuiti; CREATE TABLE log(id VARCHAR(30), timestamp VARCHAR(60), msg VARCHAR(130));"
mysql -h 10.123.123.204  -u root -pSistemiDistribuiti2017 -e "USE sistemidistribuiti; CREATE TABLE log(id VARCHAR(30), timestamp VARCHAR(60), msg VARCHAR(130));"

