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
* `profile.active` - Default: **test**. Available values: [test/prod].  
**test** - will poll 20 mocked news  
**prod** - will call real crypto-panic api, requires 

* `application` - Name of your application  
Will be included in your key schema

* `topic` - Name of the topic to which kafka will push the data  

* `poll.timeout` - Default: 60. Should be bigger than 40. Time in seconds between the poll.

* `name` - The last offset will be associated with the name given. **Side note**: for testing purposes, change name after each start.
Otherwise, the connector will keep the latest offset to track the place where he left

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

-----

Stop connector and clean up data written to Kafka

    cd kafka
    docker container stop news-connector
    docker-compose down --volumes
-----
**Setup in cluster**

    Produces fat jar crypto-panic-connector-VERSION-jar-with-dependencies.jar

    mvn clean package -DskipTests