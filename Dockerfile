FROM ubuntu:latest

RUN apt update -y && apt install -y default-jdk maven
ADD . /opt/desafio
WORKDIR /opt/desafio
RUN ./mvnw compile -f pom.xml
RUN chmod +x ./entrypoint.sh

ENTRYPOINT ["./entrypoint.sh"]

