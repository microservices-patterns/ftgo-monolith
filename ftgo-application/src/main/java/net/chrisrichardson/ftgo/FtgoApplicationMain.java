package net.chrisrichardson.ftgo;

import io.eventuate.tram.jdbckafka.TramJdbcKafkaConfiguration;
import net.chrisrichardson.ftgo.consumerservice.main.ConsumerServiceConfiguration;
import net.chrisrichardson.ftgo.courierservice.web.CourierWebConfiguration;
import net.chrisrichardson.ftgo.deliveryservice.proxy.DeliveryServiceEmbeddedConfiguration;
import net.chrisrichardson.ftgo.deliveryservice.proxy.DeliveryServiceRemoteConfiguration;
import net.chrisrichardson.ftgo.orderservice.main.OrderServiceConfiguration;
import net.chrisrichardson.ftgo.restaurantservice.RestaurantServiceConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableAutoConfiguration
@Import({ConsumerServiceConfiguration.class,
        OrderServiceConfiguration.class,
        RestaurantServiceConfiguration.class,
        CourierWebConfiguration.class,
        DeliveryServiceEmbeddedConfiguration.class,
        DeliveryServiceRemoteConfiguration.class,
        TramJdbcKafkaConfiguration.class})
public class FtgoApplicationMain {

  public static void main(String[] args) {
    SpringApplication.run(FtgoApplicationMain.class, args);
  }
}
