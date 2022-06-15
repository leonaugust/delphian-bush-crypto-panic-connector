Delphian Bush. Crypto Panic Source Connector.
-----------------
Add your properties in the following directory /config/custom-connector.properties

    name=CryptoPanicSourceConnectorDemo
    tasks.max=1
    connector.class=com.delphian.bush.CryptoPanicSourceConnector
    topic=crypto-news
    application=crypto-hoover
    crypto.panic.key=YOUR_API_KEY

-----
Start in standalone mode

    mvn clean package
    ./run.sh

-----
Stop connector

    docker container ls
    docker container stop [CONTAINER]
-----

Clean up data written to Kafka by removing all containers and volumes

    docker container prune
    docker volume prune
-----