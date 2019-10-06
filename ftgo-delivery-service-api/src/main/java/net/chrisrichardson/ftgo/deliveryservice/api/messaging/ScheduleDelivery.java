package net.chrisrichardson.ftgo.deliveryservice.api.messaging;

import io.eventuate.tram.commands.common.Command;
import net.chrisrichardson.ftgo.common.Address;

import java.time.LocalDateTime;

public class ScheduleDelivery implements Command {
  private LocalDateTime readyBy;
  private Long orderId;
  private long restaurantId;
  private Address deliveryAddress;

  public ScheduleDelivery() {
  }

  public ScheduleDelivery(LocalDateTime readyBy, Long orderId, long restaurantId, Address deliveryAddress) {
    this.readyBy = readyBy;
    this.orderId = orderId;
    this.restaurantId = restaurantId;
    this.deliveryAddress = deliveryAddress;
  }

  public LocalDateTime getReadyBy() {
    return readyBy;
  }

  public void setReadyBy(LocalDateTime readyBy) {
    this.readyBy = readyBy;
  }

  public Long getOrderId() {
    return orderId;
  }

  public long getRestaurantId() {
    return restaurantId;
  }

  public void setRestaurantId(long restaurantId) {
    this.restaurantId = restaurantId;
  }

  public Address getDeliveryAddress() {
    return deliveryAddress;
  }
}
