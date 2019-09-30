package net.chrisrichardson.ftgo.deliveryservice.domain;

import net.chrisrichardson.ftgo.common.Address;
import net.chrisrichardson.ftgo.common.UnsupportedStateTransitionException;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;

import static net.chrisrichardson.ftgo.deliveryservice.domain.DeliveryState.SCHEDULED;

@Entity
@Table(name = "delivery", catalog = "ftgo_delivery_service")
@Access(AccessType.FIELD)
@DynamicUpdate
public class Delivery {

  @Id
  private long id;

  @Enumerated(EnumType.STRING)
  @Column(name="orderState")
  private DeliveryState deliveryState ;

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

  public Delivery() {
  }

  public Delivery(long orderId, DeliveryRestaurant restaurant, Address deliveryAddress) {
    this.id = orderId;
    this.restaurant = restaurant;
    this.deliveryAddress = deliveryAddress;
    this.deliveryState = DeliveryState.PENDING;
  }

  public Long getId() {
    return id;
  }


  public void schedule(DeliveryCourier assignedCourier) {
    this.deliveryState = SCHEDULED;
    this.assignedCourier = assignedCourier;
  }


  public void notePickedUp() {
    switch (deliveryState) {
      case SCHEDULED:
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
    this.deliveryState = DeliveryState.CANCELLED;
    this.assignedCourier = null;
  }

  public DeliveryCourier getAssignedCourier() {
    return assignedCourier;
  }
}
