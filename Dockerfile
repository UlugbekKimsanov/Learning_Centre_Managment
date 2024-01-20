FROM openjdk:17
EXPOSE 8080
ARG JAR_FILE=build/libs/Learning_Centre_Managment-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} learning_center_full
ENTRYPOINT ["java","-jar", "Learning_Centre_Managment"]