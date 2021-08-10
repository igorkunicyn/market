FROM openjdk:8-jdk-alpine
#за основу взята 8 версия джавы
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} market.jar
ENTRYPOINT ["java","-jar","/market.jar"]