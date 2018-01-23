FROM debian:latest
WORKDIR .
ADD pythonfakelog.py /root/pythonfakelog.py
ADD fakelogmaker.sh  /root/fakelogmaker.sh
RUN apt-get update && apt-get install -y \
	python-pika
RUN chmod +x /root/fakelogmaker.sh
CMD ["bash"]
