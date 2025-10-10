FROM maven:3.9.9-eclipse-temurin-21 AS builder
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn clean package -DskipTests -B

FROM quay.io/wildfly/wildfly:35.0.1.Final-jdk21 AS runner

USER root
RUN if ! id -u jboss >/dev/null 2>&1; then useradd -u 1001 -r -M -d /opt/jboss jboss; fi && \
    chown -R jboss:jboss /opt/jboss
USER jboss

COPY --from=builder --chown=jboss:jboss /app/target/jakartaee-pet-adoption.war /opt/jboss/wildfly/standalone/deployments/
EXPOSE 8080

HEALTHCHECK --interval=30s --timeout=3s --start-period=5s --retries=3 \
    CMD curl -f http://localhost:8080/api/pets || exit 1
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0"]