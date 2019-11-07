package net.chrisrichardson.ftgo.apigateway;

import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.validation.constraints.NotNull;

@ConfigurationProperties(prefix = "ftgo.destinations")
public class ApiGatewayDestinations {

  @NotNull
  private String ftgoApplicationUrl;

  @NotNull
  private String deliveryServiceUrl;

  public String getFtgoApplicationUrl() {
    return ftgoApplicationUrl;
  }

  public void setFtgoApplicationUrl(String ftgoApplicationUrl) {
    this.ftgoApplicationUrl = ftgoApplicationUrl;
  }


  public String getDeliveryServiceUrl() {
    return deliveryServiceUrl;
  }

  public void setDeliveryServiceUrl(String deliveryServiceUrl) {
    this.deliveryServiceUrl = deliveryServiceUrl;
  }

}
