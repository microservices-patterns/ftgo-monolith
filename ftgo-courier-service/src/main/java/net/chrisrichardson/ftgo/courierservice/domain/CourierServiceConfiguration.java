package net.chrisrichardson.ftgo.courierservice.domain;

import net.chrisrichardson.ftgo.domain.CourierRepository;
import net.chrisrichardson.ftgo.domain.DomainConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(DomainConfiguration.class)
public class CourierServiceConfiguration {

  @Bean
  public CourierService courierService(CourierRepository courierRepository) {
    return new CourierService(courierRepository);
  }

}
