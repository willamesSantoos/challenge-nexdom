# ============================================
# BUILD STAGE (Maven + JDK)
# ============================================
FROM maven:3.8.6-eclipse-temurin-17-alpine AS builder

WORKDIR /app

# 1. Copia apenas o POM primeiro (cache eficiente)
COPY pom.xml .
# 2. Baixa todas as dependências (cache em camada separada)
RUN mvn dependency:go-offline -B

# 3. Copia o código fonte
COPY src src

# 4. Constrói o aplicativo
RUN mvn clean package -DskipTests

# ============================================
# RUNTIME STAGE (Distroless Java - Mais leve e seguro)
# ============================================
FROM gcr.io/distroless/java17-debian11

WORKDIR /app

# Argumento para versão
ARG APP_VERSION="1.0.0"
ENV SPRING_PROFILES_ACTIVE=dev

# 1. Copia apenas o JAR necessário
COPY --from=builder /app/target/*.jar app.jar

# 2. Configura o usuário não-root (já configurado no distroless)
USER nonroot

# 3. Porta exposta
EXPOSE 8080

# 4. Health Check (usando wget que está disponível no distroless)
HEALTHCHECK --interval=30s --timeout=3s \
    CMD wget --quiet --tries=1 --spider http://localhost:8080/actuator/health || exit 1

# 5. Entrypoint otimizado
ENTRYPOINT ["java", \
    "-Djava.security.egd=file:/dev/./urandom", \
    "-XX:+UseContainerSupport", \
    "-XX:MaxRAMPercentage=75.0", \
    "-XX:InitialRAMPercentage=50.0", \
    "-XX:MinRAMPercentage=50.0", \
    "-jar", "app.jar"]