FROM cassandra:3.11

RUN echo "JVM_OPTS=\"\$JVM_OPTS -Dcassandra.metricsReporterConfigFile=/usr/share/cassandra/metrics-conf/metrics-configuration.yml\"" \
    >> /etc/cassandra/cassandra-env.sh
COPY metrics-graphite-3.1.0.jar /usr/share/cassandra/lib/