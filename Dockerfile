FROM openjdk:17
RUN mkdir /app
WORKDIR /app
COPY ./build/libs/recipe-article-0.0.1-SNAPSHOT.jar ./

CMD ["java","-jar","-Dspring.profiles.active=dev","recipe-article-0.0.1-SNAPSHOT.jar"]