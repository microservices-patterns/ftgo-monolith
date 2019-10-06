package net.chrisrichardson.ftgo.deliveryservice.api.events;

public class CourierUnscheduledForDelivery implements DeliveryServiceEvent {

  private long deliveryId;

  public CourierUnscheduledForDelivery() {

  }

  public CourierUnscheduledForDelivery(long deliveryId) {
    this.deliveryId = deliveryId;
  }

  public long getDeliveryId() {
    return deliveryId;
  }

  public void setDeliveryId(long deliveryId) {
    this.deliveryId = deliveryId;
  }
}
