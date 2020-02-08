FROM openjdk:13
ADD build/libs/main-app-0.1.jar main-app-0.1.jar
EXPOSE 7701
ENTRYPOINT ["java","-jar","main-app-0.1.jar"]