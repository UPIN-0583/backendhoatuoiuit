# 🏗 Giai đoạn 1: Build JAR
FROM maven:3.9.6-eclipse-temurin-21-alpine AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# 🚀 Giai đoạn 2: Chạy ứng dụng
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app

# Copy JAR từ giai đoạn build
COPY --from=build /app/target/*.jar app.jar

# Khai báo biến PORT (Render tự set PORT thật lúc chạy)
ENV PORT=8080

# Thông báo container expose port này (không bắt buộc nhưng tốt)
EXPOSE 8080

# Lệnh chạy chính
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
