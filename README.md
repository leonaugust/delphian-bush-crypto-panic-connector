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

-----
**Testing in standalone mode**

Launch Kafka with docker-compose(starts on port *29092*)

    cd kafka
    docker-compose up
-----
Start in standalone mode

    mvn clean package
    ./run.sh

-----
Read data

    docker exec --interactive --tty kafka \
    kafka-console-consumer --bootstrap-server kafka:29092 \
    --topic news \
    --from-beginning

Stop connector

    docker container stop kafka
-----

Clean up data written to Kafka by removing all containers and volumes(*Be careful, this will delete all containers*)

    docker-compose down
    docker container prune
    docker volume prune
-----