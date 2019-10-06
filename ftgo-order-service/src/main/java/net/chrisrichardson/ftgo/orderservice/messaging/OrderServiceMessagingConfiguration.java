package net.chrisrichardson.ftgo.orderservice.messaging;

import io.eventuate.tram.events.subscriber.DomainEventDispatcher;
import io.eventuate.tram.events.subscriber.DomainEventDispatcherFactory;
import io.eventuate.tram.events.subscriber.TramEventSubscriberConfiguration;
import net.chrisrichardson.ftgo.domain.CourierRepository;
import net.chrisrichardson.ftgo.domain.OrderRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(TramEventSubscriberConfiguration.class)
public class OrderServiceMessagingConfiguration {

  @Bean
  public OrderServiceEventHandlers orderServiceEventHandlers(OrderRepository orderRepository, CourierRepository courierRepository) {
    return new OrderServiceEventHandlers(orderRepository, courierRepository);
  }

  @Bean
  public DomainEventDispatcher domainEventDispatcher(DomainEventDispatcherFactory domainEventDispatcherFactory,
                                                     OrderServiceEventHandlers orderServiceEventHandlers) {
    return domainEventDispatcherFactory.make("orderServiceEventHandlers",
            orderServiceEventHandlers.domainEventHandlers());
  }
}
