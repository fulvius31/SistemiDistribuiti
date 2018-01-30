#!/usr/bin/env python
import pika
import sys
import time
from websocket import create_connection

time.sleep(10)

connection = pika.BlockingConnection(pika.ConnectionParameters(host='10.123.123.253'))
channel = connection.channel()

channel.queue_declare(queue=sys.argv[1])

def callback(ch, method, properties, body):
    print(" [x] Received %r" % body)
    ws = create_connection("ws://localhost:8080/Manager-war/echo")
    ws.send(body)
    print "Reeiving..."
    result =  ws.recv()
    print "Received '%s'" % result
    ws.close()
channel.basic_consume(callback,
                      queue=sys.argv[1],
                      no_ack=False)

print(' [*] Waiting for messages. To exit press CTRL+C')
channel.start_consuming()
