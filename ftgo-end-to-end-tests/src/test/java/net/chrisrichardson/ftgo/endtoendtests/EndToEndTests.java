package net.chrisrichardson.ftgo.endtoendtests;

import net.chrisrichardson.ftgo.endtoendtests.common.AbstractEndToEndTests;

import java.util.Optional;

public class EndToEndTests extends AbstractEndToEndTests  {

  private String host = System.getenv("DOCKER_HOST_IP");
  private int applicationPort = Integer.parseInt(System.getProperty("ftgo.service.port", "8087"));

  @Override
  public String getHost() {
    return host;
  }

  @Override
  public int getApplicationPort() {
    return applicationPort;
  }
}
