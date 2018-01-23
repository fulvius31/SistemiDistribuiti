#!/usr/bin/env python
import pika
import sys
import time

time.sleep(8)

connection = pika.BlockingConnection(pika.ConnectionParameters(host='10.123.123.253'))
channel = connection.channel()

channel.queue_declare(queue=sys.argv[1])

def callback(ch, method, properties, body):
    print(" [x] Received %r" % body)

channel.basic_consume(callback,
                      queue=sys.argv[1],
                      no_ack=False)

print(' [*] Waiting for messages. To exit press CTRL+C')
channel.start_consuming()
