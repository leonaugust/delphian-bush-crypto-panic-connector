FROM confluentinc/cp-kafka-connect:3.2.0

WORKDIR /crypto-panic-connector
COPY config config
COPY target target

VOLUME /crypto-panic-connector/config
VOLUME /crypto-panic-connector/offsets

CMD export KAFKA_LOG4J_OPTS="-Dlog4j.configuration=logback.xml" && CLASSPATH="$(find target/ -type f -name '*.jar'| grep '\-package' | tr '\n' ':')" connect-standalone config/worker.properties config/custom-connector.properties
