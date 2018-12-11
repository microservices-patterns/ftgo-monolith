package net.chrisrichardson.ftgo.accountingservice.main;

import net.chrisrichardson.ftgo.accountingservice.domain.AccountConfiguration;
import net.chrisrichardson.ftgo.accountingservice.web.AccountingWebConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableAutoConfiguration
@EnableJpaRepositories
@EntityScan
@ComponentScan
@Import({AccountConfiguration.class, AccountingWebConfiguration.class})
public class AccountingServiceConfiguration {
}
