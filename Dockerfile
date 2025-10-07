FROM quay.io/wildfly/wildfly:latest-jdk21
COPY target/exercise6.war /opt/jboss/wildfly/standalone/deployments/
EXPOSE 8080
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0"]
