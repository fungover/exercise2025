FROM quay.io/wildfly/wildfly:latest-jdk21
COPY target/jakartaee-hello-world.war /opt/jboss/wildfly/standalone/deployments/
