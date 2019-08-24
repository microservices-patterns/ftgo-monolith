package net.chrisrichardson.ftgo.endtoendtests;

import net.chrisrichardson.ftgo.endtoendtests.common.AbstractEndToEndTests;

public class EndToEndTests extends AbstractEndToEndTests  {

  private String host = System.getenv("DOCKER_HOST_IP");
  private int applicationPort = 8081;

  @Override
  public String getHost() {
    return host;
  }

  @Override
  public int getApplicationPort() {
    return applicationPort;
  }
}
