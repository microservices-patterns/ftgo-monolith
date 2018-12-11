package net.chrisrichardson.ftgo.restaurantservice.domain;

import net.chrisrichardson.ftgo.common.Restaurant;
import net.chrisrichardson.ftgo.common.RestaurantRepository;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;

@Configuration
@EntityScan
public class RestaurantServiceDomainConfiguration {

  @Bean
  public RestaurantService restaurantService(JpaRepositoryFactoryBean restaurantRepository) {
    return new RestaurantService((RestaurantRepository) restaurantRepository.getObject());
  }

  @Bean
  public JpaRepositoryFactoryBean<RestaurantRepository, Restaurant, Long> restaurantRepository() {
    return new JpaRepositoryFactoryBean<>(RestaurantRepository.class);
  }

}
