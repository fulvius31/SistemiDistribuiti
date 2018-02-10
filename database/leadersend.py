#!/usr/bin/env python
import sys
from websocket import create_connection


line = open('/root/leader').read()
print line
ws = create_connection("ws://10.123.123.100:8080/Homework3-war/leader")
ws.send(line)
result = ws.recv()
ws.close()

