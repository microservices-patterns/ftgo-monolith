package net.chrisrichardson.ftgo.accountingservice.domain;

import net.chrisrichardson.ftgo.common.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class AccountingService {
  @Autowired
  private AccountRepository accountRepository;

  @Transactional
  public void create(Long consumerId, Money money) {
    accountRepository.save(new Account(consumerId, money));
  }
}
