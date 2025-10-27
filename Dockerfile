FROM quay.io/wildfly/wildfly:latest-jdk21
COPY target/jakartaee-pet-simulation.war /opt/jboss/wildfly/standalone/deployments/
