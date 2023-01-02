package com.delphian.bush.util.converter;

import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.data.Struct;

/**
 * origin author @OneCricketeer
 * <a href="https://stackoverflow.com/a/72567414/14308420">...</a>
 */
public interface ConnectPOJOConverter<T> {
  /**
   *
   * @return Schema of the converter.
   */
  Schema getSchema();

  /**
   *
   * @param s - Kafka Connect Struct
   * @return POJO from struct.
   */
  T fromConnectData(Struct s);

  /**
   *
   * @param t - POJO
   * @return Kafka Connect Struct from POJO
   */
  Struct toConnectData(T t);
}
