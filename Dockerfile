FROM debian:latest
WORKDIR .
ADD pythonfakelog.py /root/pythonfakelog.py
ADD ERROR_WARN.log  /root/ERROR_WARN.log
ADD fakelogmaker.sh  /root/fakelogmaker.sh
RUN apt-get update && apt-get install -y \
	python-pika
RUN chmod +x /root/pythonfakelog.py
CMD ["bash"]
