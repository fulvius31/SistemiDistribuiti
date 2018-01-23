from subprocess import Popen, call, PIPE
import errno
from types import *
import logging
import sys
import time
import argparse
import re
import shlex
import os.path
import pika
def run_command(rcmd):

    cmd = shlex.split(rcmd)
    executable = cmd[0]
    executable_options=cmd[1:]

    try:
        proc  = Popen(([executable] + executable_options), stdout=PIPE, stderr=PIPE)
        response = proc.communicate()
        response_stdout, response_stderr = response[0], response[1]
    except OSError, e:
        if e.errno == errno.ENOENT:
            logging.debug( "Unable to locate '%s' program. Is it in your path?" % executable )
        else:
            logging.error( "O/S error occured when trying to run '%s': \"%s\"" % (executable, str(e)) )
    except ValueError, e:
        logging.debug( "Value error occured. Check your parameters." )
    else:
        if proc.wait() != 0:
            logging.debug( "Executable '%s' returned with the error: \"%s\"" %(executable,response_stderr) )
            return response
        else:
            logging.debug( "Executable '%s' returned successfully. First line of response was \"%s\"" %(executable, response_stdout.split('\n')[0] ))
            return response_stdout

def sendlastoccurance():

    r = run_command("tail -n 1 /root/fake.log").strip()
    if "TOPIC1" in r:
        connection = pika.BlockingConnection(pika.ConnectionParameters(host='10.123.123.253'))
        channel = connection.channel()

        channel.queue_declare(queue='topic1')

        channel.basic_publish(exchange='',
                            routing_key='topic1',
                            body=r)
        connection.close()

    elif "TOPIC2" in r:
        connection = pika.BlockingConnection(pika.ConnectionParameters(host='10.123.123.253'))
        channel = connection.channel()

        channel.queue_declare(queue='topic2')

        channel.basic_publish(exchange='',
                            routing_key='topic2',
                            body=r)

    elif "TOPIC3" in r:
        connection = pika.BlockingConnection(pika.ConnectionParameters(host='10.123.123.253'))
        channel = connection.channel()

        channel.queue_declare(queue='topic3')

        channel.basic_publish(exchange='',
                            routing_key='topic3',
                            body=r)

    elif "TOPIC4" in r:
        connection = pika.BlockingConnection(pika.ConnectionParameters(host='10.123.123.253'))
        channel = connection.channel()

        channel.queue_declare(queue='topic4')

        channel.basic_publish(exchange='',
                            routing_key='topic4',
                            body=r)

    return r

def main():
    linea = sendlastoccurance()
    print linea
	
if __name__ == "__main__":
    main()


