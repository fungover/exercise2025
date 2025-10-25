FROM quay.io/wildfly/wildfly:34.0.0.Final-jdk21
COPY target/jakartaee-hello-world.war /opt/jboss/wildfly/standalone/deployments/
