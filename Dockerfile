FROM java:8


WORKDIR /app

COPY target/blog.jar /app/server.jar

EXPOSE 8080

RUN echo "Asia/Shanghai" > /etc/timezone

ENTRYPOINT ["java", "-jar","server.jar", "--spring.profiles.active=prod"]