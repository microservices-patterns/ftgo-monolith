package net.chrisrichardson.ftgo.deliveryservice.messaging;

import io.eventuate.tram.commands.consumer.CommandDispatcher;
import io.eventuate.tram.commands.consumer.CommandDispatcherFactory;
import io.eventuate.tram.commands.consumer.TramCommandConsumerConfiguration;
import net.chrisrichardson.ftgo.deliveryservice.api.service.DeliveryService;
import net.chrisrichardson.ftgo.deliveryservice.domain.DeliveryServiceDomainConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({DeliveryServiceDomainConfiguration.class, TramCommandConsumerConfiguration.class})
public class DeliveryServiceMessagingConfiguration {

  @Bean
  public CommandDispatcher commandDispatcher(DeliveryServiceCommandHandlers deliveryServiceCommandHandlers, CommandDispatcherFactory commandDispatcherFactory) {
    return commandDispatcherFactory.make("deliveryServiceDispatcher", deliveryServiceCommandHandlers.commandHandlers());
  }

  @Bean
  public DeliveryServiceCommandHandlers deliveryServiceCommandHandlers(DeliveryService deliveryService) {
    return new DeliveryServiceCommandHandlers(deliveryService);
  }

}
