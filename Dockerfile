FROM openjdk:17
RUN mkdir /app
WORKDIR /app
COPY ./build/libs/recipearticle-0.0.1-SNAPSHOT.jar ./

CMD ["java","-jar","-Dspring.profiles.active=dev","recipearticle-0.0.1-SNAPSHOT.jar"]