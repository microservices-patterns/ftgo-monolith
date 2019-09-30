package net.chrisrichardson.ftgo.deliveryservice.domain;

import net.chrisrichardson.ftgo.common.Address;
import net.chrisrichardson.ftgo.common.UnsupportedStateTransitionException;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Access(AccessType.FIELD)
@DynamicUpdate
public class Delivery {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(EnumType.STRING)
  @Column(name="orderState")
  private DeliveryState deliveryState;

  @ManyToOne(fetch = FetchType.LAZY)
  private DeliveryRestaurant restaurant;

  @ManyToOne
  private DeliveryCourier assignedCourier;

  @Embedded
  @AttributeOverrides({
          @AttributeOverride(name="street1", column = @Column(name="delivery_address_street1")),
          @AttributeOverride(name="street2", column = @Column(name="delivery_address_street2")),
          @AttributeOverride(name="city", column = @Column(name="delivery_address_city")),
          @AttributeOverride(name="state", column = @Column(name="delivery_address_state")),
          @AttributeOverride(name="zip", column = @Column(name="delivery_address_zip")),
  }
  )
  private Address deliveryAddress;
  private LocalDateTime deliveredTime;
  private LocalDateTime pickedUpTime;

  public Long getId() {
    return id;
  }


  public void schedule(DeliveryCourier assignedCourier) {
    this.assignedCourier = assignedCourier;
  }


  public void notePickedUp() {
    switch (deliveryState) {
      case READY_FOR_PICKUP:
        this.deliveryState = DeliveryState.PICKED_UP;
        this.pickedUpTime = LocalDateTime.now();
        return;
      default:
        throw new UnsupportedStateTransitionException(deliveryState);
    }
  }

  public void noteDelivered() {
    switch (deliveryState) {
      case PICKED_UP:
        this.deliveryState = DeliveryState.DELIVERED;
        this.deliveredTime = LocalDateTime.now();
        return;
      default:
        throw new UnsupportedStateTransitionException(deliveryState);
    }
  }


  public void cancel() {
    // TODO should be a state change: this.state = DeliveryState.CANCELLED;
    this.assignedCourier = null;
  }

  public DeliveryCourier getAssignedCourier() {
    return assignedCourier;
  }
}
