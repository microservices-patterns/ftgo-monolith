package net.chrisrichardson.ftgo.restaurantservice.lambda;

import net.chrisrichardson.ftgo.restaurantservice.domain.RestaurantServiceDomainConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(RestaurantServiceDomainConfiguration.class)
@EnableAutoConfiguration
public class RestaurantServiceLambdaConfiguration {
}
