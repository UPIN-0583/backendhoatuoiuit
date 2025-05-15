# Dùng Java 17 nhẹ từ Eclipse Temurin
FROM eclipse-temurin:17-jdk-alpine

# Tạo thư mục làm việc
WORKDIR /app

# Copy file JAR vào container
COPY target/backendhoatuoiuit-0.0.1-SNAPSHOT.jar app.jar

# Mặc định chạy ứng dụng
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
