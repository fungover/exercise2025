FROM quay.io/wildfly/wildfly:latest-jdk21
COPY target/jakartaee-pet-api.war /opt/jboss/wildfly/standalone/deployments/
