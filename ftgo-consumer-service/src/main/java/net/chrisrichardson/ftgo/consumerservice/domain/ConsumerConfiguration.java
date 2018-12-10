package net.chrisrichardson.ftgo.consumerservice.domain;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories
@EnableAutoConfiguration
@EnableTransactionManagement
@EntityScan
@ComponentScan
public class ConsumerConfiguration {

  @Bean
  public ConsumerService consumerService() {
    return new ConsumerService();
  }
}
