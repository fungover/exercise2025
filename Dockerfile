FROM quay.io/wildfly/wildfly:35.0.1.Final-jdk21
COPY target/jakartaee-hello-world.war /opt/jboss/wildfly/standalone/deployments/
