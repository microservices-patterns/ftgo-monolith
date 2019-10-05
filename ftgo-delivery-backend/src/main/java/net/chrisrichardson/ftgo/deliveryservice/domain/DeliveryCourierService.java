package net.chrisrichardson.ftgo.deliveryservice.domain;

public interface DeliveryCourierService {
  void notePickedUp(long orderId);

  void noteDelivered(long orderId);

  void updateAvailability(long courierId, boolean available);
}
