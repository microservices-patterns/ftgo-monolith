package net.chrisrichardson.ftgo.deliveryservice.proxy;

import io.eventuate.tram.commands.producer.CommandProducer;
import io.eventuate.tram.commands.producer.TramCommandProducerConfiguration;
import net.chrisrichardson.ftgo.deliveryservice.api.service.DeliveryService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(TramCommandProducerConfiguration.class)
public class DeliveryServiceRemoteConfiguration {

  @Bean
  public DeliveryService deliveryServiceProxy(CommandProducer commandProducer) {
    return new DeliveryServiceProxy(commandProducer);
  }
}
