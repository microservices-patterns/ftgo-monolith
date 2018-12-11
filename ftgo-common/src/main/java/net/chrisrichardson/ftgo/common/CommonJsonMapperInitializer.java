package net.chrisrichardson.ftgo.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

public class CommonJsonMapperInitializer {

  @Autowired
  private ObjectMapper objectMapper;

  @PostConstruct
  public void initialize() {
    objectMapper.registerModule(new MoneyModule());
  }
}
