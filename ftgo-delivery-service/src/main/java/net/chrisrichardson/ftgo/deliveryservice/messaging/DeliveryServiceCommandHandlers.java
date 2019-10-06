package net.chrisrichardson.ftgo.deliveryservice.messaging;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.eventuate.common.json.mapper.JSonMapper;
import io.eventuate.tram.commands.consumer.CommandHandlers;
import io.eventuate.tram.commands.consumer.CommandHandlersBuilder;
import io.eventuate.tram.commands.consumer.CommandMessage;
import io.eventuate.tram.commands.consumer.PathVariables;
import io.eventuate.tram.messaging.common.Message;
import net.chrisrichardson.ftgo.deliveryservice.api.messaging.CancelDelivery;
import net.chrisrichardson.ftgo.deliveryservice.api.messaging.DeliveryServiceChannels;
import net.chrisrichardson.ftgo.deliveryservice.api.messaging.ScheduleDelivery;
import net.chrisrichardson.ftgo.deliveryservice.api.service.DeliveryService;

import static io.eventuate.tram.commands.consumer.CommandHandlerReplyBuilder.withFailure;
import static io.eventuate.tram.commands.consumer.CommandHandlerReplyBuilder.withSuccess;

public class DeliveryServiceCommandHandlers {

  // TODO Figure out where to put this

  static {
    JSonMapper.objectMapper.registerModule(new JavaTimeModule());
    JSonMapper.objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
  }

  private DeliveryService deliveryService;

  public DeliveryServiceCommandHandlers(DeliveryService deliveryService) {
    this.deliveryService = deliveryService;
  }

  public CommandHandlers commandHandlers() {
    return CommandHandlersBuilder
            .fromChannel(DeliveryServiceChannels.DELIVERY_SERVICE_CHANNEL)
            .onMessage(ScheduleDelivery.class, this::scheduleDelivery)
            .onMessage(CancelDelivery.class, this::cancelDelivery)
            .build();
  }

  private Message scheduleDelivery(CommandMessage<ScheduleDelivery> cm, PathVariables pathVariables) {
    ScheduleDelivery sd = cm.getCommand();
    deliveryService.scheduleDelivery(sd.getReadyBy(), sd.getOrderId(), sd.getRestaurantId(), sd.getDeliveryAddress());
    return withSuccess();
  }

  private Message cancelDelivery(CommandMessage<CancelDelivery> cm, PathVariables pathVariables) {
    deliveryService.cancelDelivery(cm.getCommand().getOrderId());
    return withSuccess();
  }


  }
