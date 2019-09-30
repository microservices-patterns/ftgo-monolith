package net.chrisrichardson.ftgo.deliveryservice.domain;

import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;

public class CustomDeliveryCourierRepositoryImpl implements CustomDeliveryCourierRepository {

  @Autowired
  private EntityManager entityManager;

  @Override
  public DeliveryCourier findOrCreateCourier(long courierId) {
    DeliveryCourier courier = entityManager.find(DeliveryCourier.class, courierId);
    if (courier == null) {
      courier = DeliveryCourier.create(courierId);
      entityManager.persist(courier);
    }
    return courier;
  }
}
