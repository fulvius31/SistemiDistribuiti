#!/bin/bash
# this is a start script for init docker and all environment 
sudo docker-compose rm -fv
sudo docker-compose build
sudo docker-compose up -d

sleep 50
./glassfish4/bin/asadmin deploy --user=admin --passwordfile=./glassfish4/GfAdminPassword  --host=10.123.123.100 ./Homework3/dist/Homework3.ear 

for i in {200..204}
do
        ./glassfish4/bin/asadmin deploy --user=admin --passwordfile=./glassfish4/GfAdminPassword  --host=10.123.123.$i ./NodeApplication/dist/NodeApplication.ear 
done

