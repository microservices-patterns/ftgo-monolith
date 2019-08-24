package net.chrisrichardson.ftgo.restaurantservice.domain;

import net.chrisrichardson.ftgo.domain.DomainConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(DomainConfiguration.class)
public class RestaurantServiceDomainConfiguration {

  @Bean
  public RestaurantService restaurantService() {
    return new RestaurantService();
  }
}
