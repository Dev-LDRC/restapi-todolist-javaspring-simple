FROM ubuntu:latest AS build

RUN apt-get update
RUN apt-get install openjdk-17-jdk -y

COPY . .

RUN apt-get install maven -y
RUN mvn clean install -DskipTests

FROM eclipse-temurin:17-jdk-jammy

EXPOSE 8080

COPY --from=build /target/todolist-1.0.0.jar todolist-1.0.0.jar

ENTRYPOINT [ "java", "-jar", "todolist-1.0.0.jar" ]