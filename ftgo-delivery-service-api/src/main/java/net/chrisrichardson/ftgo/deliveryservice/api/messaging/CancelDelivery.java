package net.chrisrichardson.ftgo.deliveryservice.api.messaging;

import io.eventuate.tram.commands.common.Command;

public class CancelDelivery implements Command {
  private long orderId;

  public CancelDelivery() {
  }

  public CancelDelivery(long orderId) {
    this.orderId = orderId;
  }

  public long getOrderId() {
    return orderId;
  }

  public void setOrderId(long orderId) {
    this.orderId = orderId;
  }
}
