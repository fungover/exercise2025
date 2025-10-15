FROM quay.io/wildfly/wildfly:latest-jdk21
COPY target/jakartaee-adopt-a-pet.war /opt/jboss/wildfly/standalone/deployments/
