package net.chrisrichardson.ftgo.deliveryservice.proxy;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.eventuate.common.json.mapper.JSonMapper;
import io.eventuate.tram.commands.producer.CommandProducer;
import net.chrisrichardson.ftgo.common.Address;
import net.chrisrichardson.ftgo.deliveryservice.api.messaging.CancelDelivery;
import net.chrisrichardson.ftgo.deliveryservice.api.messaging.DeliveryServiceChannels;
import net.chrisrichardson.ftgo.deliveryservice.api.messaging.ScheduleDelivery;
import net.chrisrichardson.ftgo.deliveryservice.api.service.DeliveryService;

import java.time.LocalDateTime;
import java.util.Collections;

public class DeliveryServiceProxy implements DeliveryService {

  private CommandProducer commandProducer;

  public DeliveryServiceProxy(CommandProducer commandProducer) {
    this.commandProducer = commandProducer;
  }

  static {
    JSonMapper.objectMapper.registerModule(new JavaTimeModule());
    JSonMapper.objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
  }

  @Override
  public void scheduleDelivery(LocalDateTime readyBy, Long orderId, long restaurantId, Address deliveryAddress) {
    String commandId = commandProducer.send(DeliveryServiceChannels.DELIVERY_SERVICE_CHANNEL,
            new ScheduleDelivery(readyBy, orderId, restaurantId, deliveryAddress),
            "Dont_Care",
            Collections.emptyMap());
  }


  @Override
  public void cancelDelivery(long orderId) {
    String commandId = commandProducer.send(DeliveryServiceChannels.DELIVERY_SERVICE_CHANNEL,
            new CancelDelivery(orderId),
            "Dont_Care",
            Collections.emptyMap());
  }

}
