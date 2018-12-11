package net.chrisrichardson.ftgo.accountingservice.domain;

import net.chrisrichardson.ftgo.common.Money;

import javax.persistence.*;

@Entity
@Table(name = "accounts")
@Access(AccessType.FIELD)
public class Account {

  @Id
  private Long id;

  @Embedded
  private Money money;

  private Account() {
  }

  public Account(Long consumerId, Money money) {
    this.id = consumerId;
    this.money = money;
  }

  public Long getId() {
    return id;
  }

  public Long getConsumerId() {
    return id;
  }

  public Money getMoney() {
    return money;
  }

  public void setMoney(Money money) {
    this.money = money;
  }
}
