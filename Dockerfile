FROM quay.io/wildfly/wildfly:latest-jdk21
COPY target/ROOT.war /opt/jboss/wildfly/standalone/deployments/
