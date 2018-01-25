#!/bin/bash

for i in {1..1000}
do
	sleep 1
	if [ $((1 + RANDOM % 10)) -gt 5 ]
		then
		echo " $(date +%s) $(hostname) TOPIC$((1 + RANDOM %4)) with value $(( 1 + RANDOM % 50))" >> /root/fake.log
		#da qui parte uno script in python che invia i messaggi a rabbitmq
		python /root/pythonfakelog.py
	else
		echo "$(date +%s) $(hostname) i do not need it" >> /root/fake.log
	fi
done
