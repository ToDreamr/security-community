package com.pray.common;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * LocalDateSerialization
 *
 * @author 春江花朝秋月夜
 * @since 2024/1/20 18:49
 */
public class LocalDateSerialization extends JsonSerializer<LocalDateTime> {

    @Override
    public void serialize(LocalDateTime time, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(time.toString());
    }
}
