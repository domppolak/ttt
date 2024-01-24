FROM maven:3.9-amazoncorretto-21 as build
WORKDIR /usr/src/app
COPY tictactoe/pom.xml .
RUN mvn dependency:resolve-plugins dependency:resolve
ADD tictactoe /usr/src/app
RUN mvn install

FROM openjdk:21 as backend
COPY --from=build "/usr/src/app/target/*.jar" tictactoe-0.0.1-SNAPSHOT.jar
EXPOSE 8080
CMD [ "java", "-jar",  "/tictactoe-0.0.1-SNAPSHOT.jar"]
