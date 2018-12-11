package net.chrisrichardson.ftgo.accountingservice.domain;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableAutoConfiguration
@EnableJpaRepositories
@EntityScan
@ComponentScan
public class AccountConfiguration {

  @Bean
  public AccountingService accountingService() {
    return new AccountingService();
  }
}
