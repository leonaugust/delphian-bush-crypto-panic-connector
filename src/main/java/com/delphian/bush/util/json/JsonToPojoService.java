package com.delphian.bush.util.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;

@Slf4j
public abstract class JsonToPojoService<T> {

    private final ObjectMapper objectMapper;
//    private final File file;
    private final Class<T> targetClass;

    private String content;

    protected JsonToPojoService(String content, ObjectMapper objectMapper, Class<T> targetClass) {
        this.objectMapper = objectMapper;
//        this.file = file;
        this.content = content;
        this.targetClass = targetClass;
    }

    public T getFromJson() {
        try {
            return objectMapper.readValue(content, targetClass);
        } catch (IOException e) {
            log.debug("Json test service encountered unexpected exception: {}", e.getMessage());
            return null;
        }
    }

}
