package net.chrisrichardson.ftgo;

import net.chrisrichardson.ftgo.accountingservice.main.AccountingServiceConfiguration;
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
@Import({AccountingServiceConfiguration.class,
        ConsumerServiceConfiguration.class,
        OrderServiceConfiguration.class,
        RestaurantServiceConfiguration.class})
public class FtgoServiceMain {

  public static void main(String[] args) {
    SpringApplication.run(FtgoServiceMain.class, args);
  }
}
