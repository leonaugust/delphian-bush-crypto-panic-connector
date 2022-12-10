Delphian Bush. Crypto Panic Source Connector.
-----------------
Add your properties in the following directory /config/custom-connector.properties

    name=CryptoPanicSourceConnectorDemo
    tasks.max=1
    connector.class=com.delphian.bush.CryptoPanicSourceConnector
    topic=crypto-news
    application=crypto-hoover
    crypto.panic.key=YOUR_API_KEY
    profile.active=test
    poll.timeout=60

Configurable parameters:
* `profile.active` - Available values: [test/prod].  
**test** - will poll 20 mocked news  
**prod** - will call real crypto-panic api, requires 

* `application` - Name of your application  
Will be included in your key schema

* `topic` - Name of the topic to which kafka will push the data  

* `poll.timeout` - Time in seconds between the poll

* `name` - The last offset will be associated with the name given. For testing purposes, change name.
Otherwise, the connector will keep the latest offset of the records

Additional properties:
* `debug.additional.info` - Optional(default - false). Available values: [true/false].
Enables logging of the additional information.


-----
**Testing in standalone mode**

Launch Kafka with docker-compose(starts on port *29092*)

    cd kafka
    docker-compose up -d
-----
Start in standalone mode

    cd ..
    mvn clean package -DskipTests
    ./run.sh

-----
Read data

    docker exec --interactive --tty kafka \
    kafka-console-consumer --bootstrap-server kafka:29092 \
    --topic news \
    --from-beginning

Stop connector

    docker container stop news-connector
-----

Clean up data written to Kafka by removing all containers and volumes(*Be careful, this will delete all containers*)

    cd kafka
    docker-compose down --volumes
    docker container prune
    docker volume prune
-----