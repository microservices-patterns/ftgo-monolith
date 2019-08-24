package net.chrisrichardson.ftgo.orderservice.api.web;

import java.time.LocalDateTime;

public class OrderAcceptance {
  private LocalDateTime readyBy;

  public OrderAcceptance() {
  }

  public OrderAcceptance(LocalDateTime readyBy) {
    this.readyBy = readyBy;
  }

  public LocalDateTime getReadyBy() {
    return readyBy;
  }

  public void setReadyBy(LocalDateTime readyBy) {
    this.readyBy = readyBy;
  }
}

