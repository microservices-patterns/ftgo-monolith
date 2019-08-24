package net.chrisrichardson.ftgo.domain;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Embeddable
public class Action {

  @Enumerated(EnumType.STRING)
  private ActionType type;
  private LocalDateTime time;

  @ManyToOne
  private Order order;

  private Action() {
  }

  public Action(ActionType type, Order order, LocalDateTime time) {
    this.type = type;
    this.order = order;
    this.time = time;
  }

  public boolean actionFor(Order order) {
    return this.order.getId().equals(order.getId());
  }

  public static Action makePickup(Order order) {
    return new Action(ActionType.PICKUP, order, null);
  }

  public static Action makeDropoff(Order order, LocalDateTime deliveryTime) {
    return new Action(ActionType.DROPOFF, order, deliveryTime);
  }


  public ActionType getType() {
    return type;
  }

}
