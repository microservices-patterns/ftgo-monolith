package net.chrisrichardson.ftgo.deliveryservice.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

@Embeddable
public class Action {

  @Enumerated(EnumType.STRING)
  private ActionType type;
  private LocalDateTime time;

  @ManyToOne
  @JoinColumn(name="order_id", nullable=false)
  private Delivery order;

  private Action() {
  }

  public Action(ActionType type, Delivery order, LocalDateTime time) {
    this.type = type;
    this.order = order;
    this.time = time;
  }

  public boolean actionFor(Delivery order) {
    return this.order.getId().equals(order.getId());
  }

  public static Action makePickup(Delivery order) {
    return new Action(ActionType.PICKUP, order, null);
  }

  public static Action makeDropoff(Delivery order, LocalDateTime deliveryTime) {
    return new Action(ActionType.DROPOFF, order, deliveryTime);
  }


  public ActionType getType() {
    return type;
  }

}
