package net.chrisrichardson.ftgo.deliveryservice.domain;

import net.chrisrichardson.ftgo.deliveryservice.api.service.DeliveryService;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories
@EnableAutoConfiguration
@EntityScan
public class DeliveryServiceDomainConfiguration {

  @Bean
  public DeliveryServiceImpl deliveryService(DeliveryRepository deliveryRepository, DeliveryCourierRepository courierRepository, DeliveryRestaurantRepository restaurantRepository) {
    return new DeliveryServiceImpl(courierRepository, deliveryRepository, restaurantRepository);
  }
}
