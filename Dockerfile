FROM  openjdk:17
WORKDIR /src/main/java
EXPOSE 8093:8093
COPY target/AnimalsTracker-1.0.0.jar .
CMD ["java", "-jar", "AnimalsTracker-1.0.0.jar"]