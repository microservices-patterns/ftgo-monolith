package net.chrisrichardson.ftgo;

import net.chrisrichardson.ftgo.consumerservice.main.ConsumerServiceConfiguration;
import net.chrisrichardson.ftgo.orderservice.main.OrderServiceConfiguration;
import net.chrisrichardson.ftgo.restaurantservice.RestaurantServiceConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableAutoConfiguration
@ComponentScan
@Import({ConsumerServiceConfiguration.class,
        OrderServiceConfiguration.class,
        RestaurantServiceConfiguration.class})
public class FtgoApplicationMain {

  public static void main(String[] args) {
    SpringApplication.run(FtgoApplicationMain.class, args);
  }
}
