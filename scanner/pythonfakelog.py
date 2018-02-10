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
def findip():
    hostname = run_command("hostname").strip()
    with open('/root/ERROR_WARN.log', 'r') as searchfile:
            for line in searchfile:
                if "10.18.122.23" in line:
                    msg = line[:23]+" "+hostname+line[23:].split("-",1)[1]
                    time.sleep(5)
                    sendtoqueue('10.18.122.23',msg)
                if "10.18.122.24" in line:
                    msg = line[:23]+" "+hostname+line[23:].split("-",1)[1]
                    time.sleep(5)
                    sendtoqueue('10.18.122.24',msg)
                if "10.18.122.30" in line:
                    msg = line[:23]+" "+hostname+line[23:].split("-",1)[1]
                    time.sleep(5)
                    sendtoqueue('10.18.122.30',msg)

def sendtoqueue(iptopic,msg):

        connection = pika.BlockingConnection(pika.ConnectionParameters(host='10.123.123.253'))
        channel = connection.channel()

        channel.queue_declare(queue=iptopic)

        channel.basic_publish(exchange='',
                            routing_key=iptopic,
                            body=msg)
        connection.close()

def main():
    time.sleep(100)
    findip()	
if __name__ == "__main__":
    main()


