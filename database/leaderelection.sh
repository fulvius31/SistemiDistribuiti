#!/bin/bash
socat tcp-l:1111,reuseaddr,fork system:'cat /root/myid',nofork &
while true
do
	sleep 15
	leader=$(cat /root/leader)
	leaderisdead="false"
	backupisdead="false"
	subnet="10.123.123"
	for i in {200..204}
	do
		ping -c 1 $subnet.$i > /dev/null 2>&1
		if [ $? -eq 0 ]; then
			echo "$subnet.$i is alive"
			if [ $subnet.$i = $leader ]; then
				cat /root/myid > /root/comparison
				echo "$subnet.$i It's the leader"
			else
				echo "It's not the leader"
			fi
		else
			if [ $subnet.$i = $leader ]; then
				echo "$subnet.$i is dead"
				leaderisdead="true"
				break
			else
				backupisdead="true"
				break
			fi
		fi
	done

	if [ $leaderisdead = "true" ]; then
		echo "Sending my id..."
		for i in {200..204}
		do
			nc  -v $subnet.$i 1111 >> /root/comparison
		done
	
		sleep 5
		numids=$( cat /root/comparison | wc -l )
		if [ $numids -gt 1 ]; then
			possibileader=$(sort -r /root/comparison | head -n 1 | sed -e 's/^/10.123.123.20/')
			echo $possibileader > /root/leader
			python /root/leadersend.py
		fi
	fi
	if [ $backupisdead = "true" ]; then
		sed -i "/$subnet.$i/d" /root/alivereplicas.txt
	fi
done
