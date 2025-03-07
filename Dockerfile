FROM flink:1.17-scala_2.12

WORKDIR /opt/flink

COPY target/flink-loan-application-0.1.jar /opt/flink/usrlib/

EXPOSE 8081
EXPOSE 6123

CMD ["sh", "-c", "bin/jobmanager.sh start-foreground & bin/taskmanager.sh start-foreground & bin/flink run /opt/flink/usrlib/flink-loan-application-0.1.jar"]





