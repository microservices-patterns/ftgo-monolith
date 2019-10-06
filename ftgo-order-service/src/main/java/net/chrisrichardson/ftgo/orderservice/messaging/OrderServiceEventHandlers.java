package net.chrisrichardson.ftgo.orderservice.messaging;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.eventuate.common.json.mapper.JSonMapper;
import io.eventuate.tram.events.common.DomainEvent;
import io.eventuate.tram.events.subscriber.DomainEventEnvelope;
import io.eventuate.tram.events.subscriber.DomainEventHandlers;
import io.eventuate.tram.events.subscriber.DomainEventHandlersBuilder;
import net.chrisrichardson.ftgo.deliveryservice.api.events.ActionDto;
import net.chrisrichardson.ftgo.deliveryservice.api.events.CourierScheduledForDelivery;
import net.chrisrichardson.ftgo.deliveryservice.api.events.CourierUnscheduledForDelivery;
import net.chrisrichardson.ftgo.deliveryservice.api.events.DeliveryServiceChannels;
import net.chrisrichardson.ftgo.domain.*;

import java.util.stream.Collectors;

public class OrderServiceEventHandlers {

  static {
    JSonMapper.objectMapper.registerModule(new JavaTimeModule());
    JSonMapper.objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
  }

  private OrderRepository orderRepository;
  private CourierRepository courierRepository;

  public OrderServiceEventHandlers(OrderRepository orderRepository, CourierRepository courierRepository) {
    this.orderRepository = orderRepository;
    this.courierRepository = courierRepository;
  }

  public DomainEventHandlers domainEventHandlers() {
    return DomainEventHandlersBuilder
            .forAggregateType(DeliveryServiceChannels.COURIER_CHANNEL)
            .onEvent(CourierScheduledForDelivery.class, this::handleCourierScheduledForDelivery)
            .onEvent(CourierUnscheduledForDelivery.class, this::handleCourierUnscheduledForDelivery)
            .build();
  }

  private void handleCourierScheduledForDelivery(DomainEventEnvelope<CourierScheduledForDelivery> dee) {
    Order order = orderRepository.findById(dee.getEvent().getDeliveryId()).get();
    Courier courier = courierRepository.findById(Long.parseLong(dee.getAggregateId())).get();
    order.noteCourierAssigned(order, courier);
    courier.addActions(dee.getEvent().getActions().stream().map(dto -> toAction(order, dto)).collect(Collectors.toList()));
  }

  private static Action toAction(Order order, ActionDto actionDto) {
    return new Action(ActionType.valueOf(actionDto.getType()), order, actionDto.getTime());
  }

  private void handleCourierUnscheduledForDelivery(DomainEventEnvelope<CourierUnscheduledForDelivery> dee) {
    Order order = orderRepository.findById(dee.getEvent().getDeliveryId()).get();
    Courier courier = courierRepository.findById(Long.parseLong(dee.getAggregateId())).get();
    order.noteCourierAssigned(order, null);
    courier.removeActionsForOrder(order);
  }


}
