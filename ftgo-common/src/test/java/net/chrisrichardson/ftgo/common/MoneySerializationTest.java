package net.chrisrichardson.ftgo.common;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.util.Assert;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class MoneySerializationTest {

  private static ObjectMapper objectMapper = new ObjectMapper();

  @BeforeClass
  public static void initialize() {
    objectMapper.registerModule(new MoneyModule());
  }


  public static class MoneyContainer {
    private Money price;

    @Override
    public boolean equals(Object o) {
      return EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
      return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
      return ToStringBuilder.reflectionToString(this);
    }

    public Money getPrice() {
      return price;
    }

    public void setPrice(Money price) {
      this.price = price;
    }

    public MoneyContainer() {

    }

    public MoneyContainer(Money price) {

      this.price = price;
    }
  }

  @Test
  public void shouldSer() throws IOException {
    Money price = new Money("12.34");
    MoneyContainer mc = new MoneyContainer(price);
    assertEquals("{\"price\":\"12.34\"}", objectMapper.writeValueAsString(mc));
  }

  @Test
  public void shouldDe() throws IOException  {
    Money price = new Money("12.34");
    MoneyContainer mc = new MoneyContainer(price);
    assertEquals(mc, objectMapper.readValue("{\"price\":\"12.34\"}", MoneyContainer.class));
  }

  @Test
  public void shouldFailToDe() throws IOException  {
    JsonMappingException jsonMappingException = null;
    try {
      objectMapper.readValue("{\"price\": { \"amount\" : \"12.34\"} }", MoneyContainer.class);
      fail("expected exception");
    } catch (JsonMappingException e) {
      jsonMappingException = e;
    }
    Assert.notNull(jsonMappingException);
  }


}