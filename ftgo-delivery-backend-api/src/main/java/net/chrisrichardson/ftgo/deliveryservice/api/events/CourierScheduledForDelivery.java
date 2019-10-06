package net.chrisrichardson.ftgo.deliveryservice.api.events;

import java.util.List;

public class CourierScheduledForDelivery implements DeliveryServiceEvent {
  private long deliveryId;
  private List<ActionDto> actions;

  public CourierScheduledForDelivery() {

  }

  public CourierScheduledForDelivery(long deliveryId, List<ActionDto> actions) {
    this.deliveryId = deliveryId;
    this.actions = actions;
  }

  public long getDeliveryId() {
    return deliveryId;
  }

  public void setDeliveryId(long deliveryId) {
    this.deliveryId = deliveryId;
  }

  public List<ActionDto> getActions() {
    return actions;
  }

  public void setActions(List<ActionDto> actions) {
    this.actions = actions;
  }
}
