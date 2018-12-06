package net.chrisrichardson.ftgo.accountingservice.messaging;

import io.eventuate.javaclient.spring.EnableEventHandlers;
import io.eventuate.tram.commands.consumer.CommandDispatcher;
import io.eventuate.tram.consumer.kafka.DuplicateMessageDetector;
import io.eventuate.tram.events.subscriber.DomainEventDispatcher;
import io.eventuate.tram.messaging.consumer.MessageConsumer;
import io.eventuate.tram.sagas.eventsourcingsupport.SagaReplyRequestedEventSubscriber;
import net.chrisrichardson.ftgo.accountingservice.domain.Account;
import net.chrisrichardson.ftgo.accountingservice.domain.AccountConfiguration;
import net.chrisrichardson.ftgo.common.CommonConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.Collections;

@Configuration
@ComponentScan
@EnableAutoConfiguration
@EnableEventHandlers
@Import(AccountConfiguration.class)
public class AccountingMessagingConfiguration {

  @Bean
  public AccountingEventConsumer accountingEventConsumer() {
    return new AccountingEventConsumer();
  }

  @Bean
  public DomainEventDispatcher accountingServiceDomainEventDispatcher(AccountingEventConsumer accountingEventConsumer, MessageConsumer messageConsumer) {
    return new DomainEventDispatcher("accountingServiceDomainEventDispatcher", accountingEventConsumer.domainEventHandlers(), messageConsumer);
  }

  @Bean
  public AccountingServiceCommandHandler accountCommandHandler() {
    return new AccountingServiceCommandHandler();
  }


  @Bean
  public CommandDispatcher accountingServiceCommandDispatcher(AccountingServiceCommandHandler target,
                                                              AccountServiceChannelConfiguration data) {
    return new CommandDispatcher(data.getCommandDispatcherId(), target.commandHandlers());
  }

  @Bean
  public DuplicateMessageDetector duplicateMessageDetector() {
    return new NoopDuplicateMessageDetector();
  }

  @Bean
  public AccountServiceChannelConfiguration accountServiceChannelConfiguration() {
    return new AccountServiceChannelConfiguration("accountCommandDispatcher", "accountCommandChannel");
  }

  @Bean
  public SagaReplyRequestedEventSubscriber sagaReplyRequestedEventSubscriber() {
    return new SagaReplyRequestedEventSubscriber("accountingServiceSagaReplyRequestedEventSubscriber", Collections.singleton(Account.class.getName()));
  }

}
