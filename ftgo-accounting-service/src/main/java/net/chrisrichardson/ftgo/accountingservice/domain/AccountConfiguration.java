package net.chrisrichardson.ftgo.accountingservice.domain;

import io.eventuate.tram.commands.producer.TramCommandProducerConfiguration;
import net.chrisrichardson.ftgo.common.CommonConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableAutoConfiguration
@EnableJpaRepositories
@EntityScan
@ComponentScan
@Import({TramCommandProducerConfiguration.class, CommonConfiguration.class})
public class AccountConfiguration {

  @Bean
  public AccountingService accountingService() {
    return new AccountingService();
  }
}
