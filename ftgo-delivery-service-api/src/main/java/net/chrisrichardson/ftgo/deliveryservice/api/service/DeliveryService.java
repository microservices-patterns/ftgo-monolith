package net.chrisrichardson.ftgo.deliveryservice.api.service;

import java.time.LocalDateTime;

public interface DeliveryService  {
  void scheduleDelivery(LocalDateTime readyBy, Long orderId);

  void cancelDelivery(long orderId);

}
