package net.chrisrichardson.ftgo.deliveryservice.domain;

import io.eventuate.tram.events.publisher.DomainEventPublisher;
import io.eventuate.tram.events.publisher.TramEventsPublisherConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories
@EnableAutoConfiguration
@EntityScan
@Import(TramEventsPublisherConfiguration.class)
public class DeliveryServiceDomainConfiguration {

  @Bean
  public DeliveryServiceImpl deliveryService(DeliveryRepository deliveryRepository, DeliveryCourierRepository courierRepository, DeliveryRestaurantRepository restaurantRepository,
                                             DomainEventPublisher domainEventPublisher) {
    return new DeliveryServiceImpl(courierRepository, deliveryRepository, restaurantRepository, domainEventPublisher);
  }
}
