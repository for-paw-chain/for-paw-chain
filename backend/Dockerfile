FROM gradle:7.4-jdk11-alpine as builder
WORKDIR /build

# 그래들 파일이 변경되었을 때만 새롭게 의존패키지 다운로드 받게함.
COPY build.gradle settings.gradle /build/
RUN gradle build -x test --parallel --continue > /dev/null 2>&1 || true

# 빌더 이미지에서 애플리케이션 빌드
COPY . /build
RUN gradle build -x test --parallel

# open jdk java 11 버전 환경
FROM openjdk:11-jdk

# gradle 빌드 시 jar 파일 생성 경로
ARG JAR_FILE=./build/libs/*.jar

# JAR_FILE을 메인 디렉토리에 복사
COPY ${JAR_FILE} app.jar

# 8080 port opne
EXPOSE 8080

# 시스템 진입점
ENTRYPOINT ["java","-jar","app.jar"]