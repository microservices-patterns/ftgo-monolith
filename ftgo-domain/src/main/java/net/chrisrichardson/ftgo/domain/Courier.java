package net.chrisrichardson.ftgo.domain;

import net.chrisrichardson.ftgo.common.Address;
import net.chrisrichardson.ftgo.common.PersonName;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.List;

@Entity
@Access(AccessType.FIELD)
@DynamicUpdate
public class Courier {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Embedded
  private PersonName name;

  @Embedded
  private Address address;

  @Embedded
  private Plan plan;

  private Boolean available = false;

  public Courier() {
  }

  public Courier(PersonName name, Address address) {
    this.name = name;
    this.address = address;
  }

  public Long getId() {
    return id;
  }

  public Plan getPlan() {
    return plan;
  }

  public List<Action> actionsForDelivery(Order order) {
    return plan.actionsForDelivery(order);
  }

  public void addActions(List<Action> actions) {
    plan.addActions(actions);
  }

  public void removeActionsForOrder(Order order) {
    plan.removeActionsForOrder(order);
  }
}
