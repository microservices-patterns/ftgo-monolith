package net.chrisrichardson.ftgo.deliveryservice.domain;

import net.chrisrichardson.ftgo.deliveryservice.api.service.DeliveryService;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

public class DeliveryServiceImpl implements DeliveryService, DeliveryCourierService {

  private Random random = new Random();
  private DeliveryCourierRepository courierRepository;
  private DeliveryRepository deliveryRepository;

  public DeliveryServiceImpl(DeliveryCourierRepository courierRepository, DeliveryRepository deliveryRepository) {
    this.courierRepository = courierRepository;
    this.deliveryRepository = deliveryRepository;
  }

  @Override
  public void scheduleDelivery(LocalDateTime readyBy, Long orderId) {

    Delivery delivery = deliveryRepository.findById(orderId).get();

    // Stupid implementation

    List<DeliveryCourier> couriers = courierRepository.findAllAvailable();
    DeliveryCourier courier = couriers.get(random.nextInt(couriers.size()));
    courier.addAction(Action.makePickup(delivery));
    courier.addAction(Action.makeDropoff(delivery, readyBy.plusMinutes(30)));

    delivery.schedule(courier);

  }

  @Override
  @Transactional
  public void notePickedUp(long orderId) {
    Delivery delivery = deliveryRepository.findById(orderId).get();
    delivery.notePickedUp();
  }

  @Override
  @Transactional
  public void noteDelivered(long orderId) {
    Delivery delivery = deliveryRepository.findById(orderId).get();
    delivery.noteDelivered();
  }

  @Override
  public void cancelDelivery(long orderId) {
    Delivery delivery = deliveryRepository.findById(orderId).get();
    DeliveryCourier assignedCourier = delivery.getAssignedCourier();
    delivery.cancel();
    if (assignedCourier != null) {
      assignedCourier.cancelDelivery(delivery);
    }
  }

  @Override
  @Transactional
  public void updateAvailability(long courierId, boolean available) {
    if (available)
      noteAvailable(courierId);
    else
      noteUnavailable(courierId);
  }

  void noteAvailable(long courierId) {
    courierRepository.findOrCreateCourier(courierId).noteAvailable();
  }

  void noteUnavailable(long courierId) {
    courierRepository.findOrCreateCourier(courierId).noteUnavailable();
  }


}
