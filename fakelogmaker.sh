#!/bin/bash

for i in {1..1000}
do
	sleep 1
	if [ $((1 + RANDOM % 10)) -gt 5 ]
		then
		echo " $(date +%s) $(hostname) TOPIC$((1 + RANDOM %4)) with value $(( 1 + RANDOM % 50))" >> /root/fake.log
		python /root/pythonfakelog.py
	else
		echo "FUFFAAAA" >> /root/fake.log
	fi
done
