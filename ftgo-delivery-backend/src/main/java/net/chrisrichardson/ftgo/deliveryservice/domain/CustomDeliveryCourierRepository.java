package net.chrisrichardson.ftgo.deliveryservice.domain;

public interface CustomDeliveryCourierRepository {

  DeliveryCourier findOrCreateCourier(long courierId);

}
