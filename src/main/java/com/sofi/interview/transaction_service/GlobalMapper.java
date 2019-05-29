package com.sofi.interview.transaction_service;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import java.time.LocalDateTime;

public enum GlobalMapper {
  INSTANCE;

  private static ObjectMapper mapper = new ObjectMapper();
  private static ObjectMapper prettyMapper = new ObjectMapper();

  static {
    addStandardSettings(mapper);
    addStandardSettings(prettyMapper).enable(SerializationFeature.INDENT_OUTPUT);
  }

  public static ObjectMapper addStandardSettings(ObjectMapper mapper) {
    mapper = mapper
        .setSerializationInclusion(NON_NULL)
        .registerModule(new ParameterNamesModule());

    // The settings are required to properly serialize and deserialize
    // LocalDateTime and OffsetDateTime to the proper string format
    final JavaTimeModule javaTimeModule = new JavaTimeModule();
    javaTimeModule
        .addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(ISO_LOCAL_DATE_TIME))
        .addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(ISO_LOCAL_DATE_TIME));

    mapper = mapper
        .registerModule(new Jdk8Module())
        .registerModule(javaTimeModule)
        .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    return mapper;
  }

  public ObjectMapper mapper() {
    return mapper;
  }

  public ObjectMapper prettyMapper() {
    return prettyMapper;
  }
}
