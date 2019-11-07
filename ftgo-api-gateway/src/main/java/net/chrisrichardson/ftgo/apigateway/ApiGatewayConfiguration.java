package net.chrisrichardson.ftgo.apigateway;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableConfigurationProperties({ApiGatewayDestinations.class})
public class ApiGatewayConfiguration {

  @Bean
  public RouteLocator routing(RouteLocatorBuilder builder, ApiGatewayDestinations apiGatewayDestinations) {
    return builder.routes()

            // route to Delivery Service - DeliveryController

            .route(r -> r.path("/couriers/**/availability").uri(apiGatewayDestinations.getDeliveryServiceUrl()))
            .route(r -> r.path("/orders/**/pickedup").uri(apiGatewayDestinations.getDeliveryServiceUrl()))
            .route(r -> r.path("/orders/**/delivered").uri(apiGatewayDestinations.getDeliveryServiceUrl()))

            // everything else goes to monolith

            .route(r -> r.path("/**").uri(apiGatewayDestinations.getFtgoApplicationUrl()))
            .build();
  }

}
