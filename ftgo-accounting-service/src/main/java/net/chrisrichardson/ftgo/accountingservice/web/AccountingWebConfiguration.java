package net.chrisrichardson.ftgo.accountingservice.web;

import net.chrisrichardson.ftgo.accountingservice.domain.AccountConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(AccountConfiguration.class)
@ComponentScan
public class AccountingWebConfiguration {
}
