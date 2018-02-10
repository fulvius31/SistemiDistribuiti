#!/bin/bash 

for i in {200..204}
do
	./asadmin deploy --user=admin --passwordfile=/home/fulvius/SistemiDistribuiti/SistemiDistribuiti/GfAdminPassword  --host=10.123.123.$i /home/fulvius/SistemiDistribuiti/SistemiDistribuiti/NodeApplication/dist/NodeApplication.ear 
done
