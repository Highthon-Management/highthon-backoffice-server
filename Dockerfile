FROM openjdk:17-jdk as builder
ARG JAR_FILE=build/libs/*.jar
ADD ${JAR_FILE} app.jar

FROM openjdk:17-jdk-slim
COPY --from=builder /app.jar /app.jar

ENV TZ=Asia/Seoul

EXPOSE 8080

ENTRYPOINT ["java","-jar","/app.jar"]