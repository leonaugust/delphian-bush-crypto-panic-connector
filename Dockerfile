FROM confluentinc/cp-kafka-connect:3.2.0
#3.2.0
#6.2.4

WORKDIR /crypto-panic-connector
COPY config config
COPY target target

VOLUME /crypto-panic-connector/config
VOLUME /crypto-panic-connector/offsets

#RUN ls target

CMD CLASSPATH="$(find target/ -type f -name '*.jar'| grep '\-package' | tr '\n' ':')" connect-standalone config/worker.properties config/custom-connector.properties