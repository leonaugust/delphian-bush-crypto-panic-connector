package com.delphian.bush.util.converter;

import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.data.Struct;

public interface ConnectPOJOConverter<T> {
  Schema getSchema();
  T fromConnectData(Struct s);
  Struct toConnectData(T t);
}
