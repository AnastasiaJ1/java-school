FROM maven:3.8.5-openjdk-18 as build
#WORKDIR /app/

COPY pom.xml .

COPY . .

RUN mvn clean

RUN mvn -B package -DskipTests

FROM openjdk:18
#WORKDIR /app
COPY --from=build /app/target/app.jar app.jar
#ADD log4j2.xml log4j2.xml
ADD ./services/src/main/resources/log4j2.xml /services/src/main/resources/log4j2.xml
ENTRYPOINT ["java", "-jar","/app.jar"]

