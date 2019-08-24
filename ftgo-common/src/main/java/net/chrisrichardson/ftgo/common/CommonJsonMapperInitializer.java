package net.chrisrichardson.ftgo.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

public class CommonJsonMapperInitializer {

  @Autowired
  private ObjectMapper objectMapper;

  @PostConstruct
  public void initialize() {
    objectMapper.registerModule(new MoneyModule());
    objectMapper.registerModule(new JavaTimeModule());
    objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

  }
}
