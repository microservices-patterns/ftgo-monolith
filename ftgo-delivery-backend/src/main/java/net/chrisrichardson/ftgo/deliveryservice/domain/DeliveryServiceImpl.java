package net.chrisrichardson.ftgo.deliveryservice.domain;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.eventuate.common.json.mapper.JSonMapper;
import io.eventuate.tram.events.publisher.DomainEventPublisher;
import net.chrisrichardson.ftgo.common.Address;
import net.chrisrichardson.ftgo.deliveryservice.api.events.ActionDto;
import net.chrisrichardson.ftgo.deliveryservice.api.events.CourierScheduledForDelivery;
import net.chrisrichardson.ftgo.deliveryservice.api.events.CourierUnscheduledForDelivery;
import net.chrisrichardson.ftgo.deliveryservice.api.events.DeliveryServiceChannels;
import net.chrisrichardson.ftgo.deliveryservice.api.service.DeliveryService;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class DeliveryServiceImpl implements DeliveryService, DeliveryCourierService {

  static {
    JSonMapper.objectMapper.registerModule(new JavaTimeModule());
    JSonMapper.objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
  }

  private Random random = new Random();
  private DeliveryCourierRepository courierRepository;
  private DeliveryRepository deliveryRepository;
  private DeliveryRestaurantRepository restaurantRepository;
  private DomainEventPublisher domainEventPublisher;

  public DeliveryServiceImpl(DeliveryCourierRepository courierRepository, DeliveryRepository deliveryRepository, DeliveryRestaurantRepository restaurantRepository,
                              DomainEventPublisher domainEventPublisher) {
    this.courierRepository = courierRepository;
    this.deliveryRepository = deliveryRepository;
    this.restaurantRepository = restaurantRepository;
    this.domainEventPublisher = domainEventPublisher;
  }

  @Override
  public void scheduleDelivery(LocalDateTime readyBy, Long orderId, long restaurantId, Address deliveryAddress) {

    // TODO readyBy unused

    DeliveryRestaurant restaurant = restaurantRepository.findById(restaurantId).get();

    Delivery delivery = new Delivery(orderId, restaurant, deliveryAddress);

    delivery = deliveryRepository.save(delivery);

    // Stupid implementation

    List<DeliveryCourier> couriers = courierRepository.findAllAvailable();
    DeliveryCourier courier = couriers.get(random.nextInt(couriers.size()));

    Action pickup = Action.makePickup(delivery);
    Action dropoff = Action.makeDropoff(delivery, readyBy.plusMinutes(30));

    courier.addAction(pickup);
    courier.addAction(dropoff);

    delivery.schedule(courier);

    domainEventPublisher.publish(DeliveryServiceChannels.COURIER_CHANNEL, courier.getId(),
            Collections.singletonList(new CourierScheduledForDelivery(delivery.getId(), Arrays.asList(makeDto(pickup), makeDto(dropoff)))));

  }

  private static ActionDto makeDto(Action action) {
    return new ActionDto(action.getType().name(), action.getTime());
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
    deliveryRepository.findById(orderId).ifPresent(delivery -> {
      DeliveryCourier assignedCourier = delivery.getAssignedCourier();
      delivery.cancel();
      if (assignedCourier != null) {
        assignedCourier.cancelDelivery(delivery);
        domainEventPublisher.publish(DeliveryServiceChannels.COURIER_CHANNEL, assignedCourier.getId(),
                Collections.singletonList(new CourierUnscheduledForDelivery(delivery.getId())));
      }
    });
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
