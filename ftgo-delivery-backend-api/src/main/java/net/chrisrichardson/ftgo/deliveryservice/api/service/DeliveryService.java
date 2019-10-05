package net.chrisrichardson.ftgo.deliveryservice.api.service;

import net.chrisrichardson.ftgo.common.Address;

import java.time.LocalDateTime;

public interface DeliveryService  {
  void scheduleDelivery(LocalDateTime readyBy, Long orderId, long restaurantId, Address deliveryAddress);
  void cancelDelivery(long orderId);

}
