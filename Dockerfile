FROM openjdk:13

EXPOSE 7701

COPY build/libs/main-app-0.1.jar main-app-0.1.jar

ENTRYPOINT ["java","-jar","main-app-0.1.jar"]
