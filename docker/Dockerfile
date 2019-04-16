FROM java:8-alpine

RUN mkdir -p /opt/app

ADD application/build/distributions/application-1.0.tar /opt/app/

EXPOSE 8181
ENV rikchallenge_url = jdbc:mysql://dockerhost:3306/rikchallenge
CMD ["/opt/app/application-1.0/bin/application"]