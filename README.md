Delphian Bush. Crypto Panic Source Connector.
-----------------
Add your properties in the following directory /config/custom-connector.properties

name=CryptoPanicSourceConnectorDemo
tasks.max=1
connector.class=com.delphian.bush.CryptoPanicSourceConnector
topic=crypto-news
application=crypto-hoover
crypto.panic.key=YOUR_API_KEY

===================
docker rm -f $(docker ps -a -q)
docker volume rm $(docker volume ls -q)

OR
docker container prune

-----
    mvn clean package
    ./run.sh

-----

docker container ls
docker container stop [CONTAINER]