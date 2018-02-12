# DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
# 
# Copyright (c) 2015-2017 Oracle and/or its affiliates. All rights reserved.
# 
# GlassFish on Docker with Oracle Linux and OpenJDK
FROM oraclelinux:7-slim

# Maintainer
MAINTAINER Arindam Bandyopadhyay<arindam.bandyopadhyay@oracle.com> and mysql integrating by fulvius<mail@alessandrosangiorgi.net>
#MYSQL URLs
ARG PACKAGE_URL=https://repo.mysql.com/yum/mysql-5.7-community/docker/x86_64/mysql-community-server-minimal-5.7.21-1.el7.x86_64.rpm
ARG PACKAGE_URL_SHELL=https://repo.mysql.com/yum/mysql-tools-community/el/7/x86_64/mysql-shell-1.0.11-1.el7.x86_64.rpm
# Set environment variables and default password for user 'admin'
ENV GLASSFISH_PKG=glassfish-4.1.1.zip \
    GLASSFISH_URL=https://download.oracle.com/glassfish/4.1.1/release/glassfish-4.1.1.zip \
    GLASSFISH_HOME=/glassfish4 \
    MD5=4e7ce65489347960e9797d2161e0ada2 \
    PATH=$PATH:/glassfish4/bin \
    JAVA_HOME=/usr/lib/jvm/java-openjdk
#Env vars for mysql
ENV MYSQL_ROOT_PASSWORD=SistemiDistribuiti2017
ENV MYSQL_USER=root
ENV MYSQL_PASSWORD=SistemiDistribuiti2017
ENV MYSQL_DATABASE=sistemidistribuiti

# Install packages, download and extract GlassFish
# Setup password file
# Enable DAS
RUN yum -y install unzip java-1.8.0-openjdk-devel mysql python python-pip && \
    curl -O $GLASSFISH_URL && \
    echo "$MD5 *$GLASSFISH_PKG" | md5sum -c - && \
    unzip -o $GLASSFISH_PKG && \
    rm -f $GLASSFISH_PKG && \
    yum -y remove unzip && \
    rm -rf /var/cache/yum
#Remove mariadb and mariadb-libs for conflicts with mysql
RUN yum -y remove mariadb mariadb-libs
#install pip for websocket in python 
RUN curl "https://bootstrap.pypa.io/get-pip.py" -o "get-pip.py"
RUN python get-pip.py
RUN pip install websocket-client

# Install server
RUN rpmkeys --import https://repo.mysql.com/RPM-GPG-KEY-mysql \
  && yum install -y $PACKAGE_URL $PACKAGE_URL_SHELL libpwquality \
  && yum clean all \
  && mkdir /docker-entrypoint-initdb.d

#All configs for project
ARG id
ADD ./leaderelection.sh /root/leaderelection.sh
ADD ./leader /root/leader
RUN echo $id >  /root/myid
RUN yum -y  install \
	netcat nmap-ncat  iputils socat 
RUN chmod +x /root/leaderelection.sh

VOLUME /var/lib/mysql
COPY alivereplicas /root/alivereplicas.txt
COPY leadersend.py /root/leadersend.py
COPY docker-entrypoint.sh /entrypoint.sh
RUN chmod +x /entrypoint.sh
ENTRYPOINT ["/entrypoint.sh"]
HEALTHCHECK CMD /healthcheck.sh
# Ports being exposed
EXPOSE 4848 8080 8181
EXPOSE 3306 33060

# Start asadmin console and the domain
CMD ["asadmin", "start-domain", "-v"]

