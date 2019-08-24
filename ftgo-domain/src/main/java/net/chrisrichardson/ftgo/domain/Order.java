package net.chrisrichardson.ftgo.domain;

import net.chrisrichardson.ftgo.common.Money;
import net.chrisrichardson.ftgo.common.UnsupportedStateTransitionException;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

import static net.chrisrichardson.ftgo.domain.OrderState.*;


@Entity
@Table(name = "orders")
@Access(AccessType.FIELD)
@DynamicUpdate
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Version
  private Long version;

  @Enumerated(EnumType.STRING)
  private OrderState orderState;

  private Long consumerId;

  @ManyToOne(fetch = FetchType.LAZY)
  private Restaurant restaurant;

  @Embedded
  private OrderLineItems orderLineItems;

  @Embedded
  private DeliveryInformation deliveryInformation;

  @Embedded
  private PaymentInformation paymentInformation;

  @Embedded
  @AttributeOverride(name="amount", column = @Column(name="order_minimum"))
  private Money orderMinimum = new Money(Integer.MAX_VALUE);

  private LocalDateTime readyBy;
  private LocalDateTime acceptTime;
  private LocalDateTime preparingTime;
  private LocalDateTime readyForPickupTime;
  private LocalDateTime pickedUpTime;
  private LocalDateTime deliveredTime;

  @ManyToOne
  private Courier assignedCourier;

  private Order() {
  }

  public Order(long consumerId, Restaurant restaurant, List<OrderLineItem> orderLineItems) {
    this.consumerId = consumerId;
    this.restaurant = restaurant;
    this.orderLineItems = new OrderLineItems(orderLineItems);
    this.orderState = APPROVED;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }



  public Money getOrderTotal() {
    return orderLineItems.orderTotal();
  }

  public void cancel() {

    if (orderState != APPROVED) {
      throw new UnsupportedStateTransitionException(orderState);
    }

    this.orderState = CANCELLED;

  }

  public void revise(OrderRevision orderRevision) {
    if (orderState == APPROVED) {
      LineItemQuantityChange change = orderLineItems.lineItemQuantityChange(orderRevision);
      if (change.newOrderTotal.isGreaterThanOrEqual(orderMinimum)) {
        throw new OrderMinimumNotMetException();
      }
    } else {
      throw new UnsupportedStateTransitionException(orderState);
    }

    orderRevision.getDeliveryInformation().ifPresent(newDi -> this.deliveryInformation = newDi);

    if (!orderRevision.getRevisedLineItemQuantities().isEmpty()) {
      orderLineItems.updateLineItems(orderRevision);
    }

    orderRevision.getDeliveryInformation().ifPresent(newDi -> this.deliveryInformation = newDi);
    if (!orderRevision.getRevisedLineItemQuantities().isEmpty()) {
      orderLineItems.updateLineItems(orderRevision);
    }

  }


  public Long getVersion() {
    return version;
  }

  public List<OrderLineItem> getLineItems() {
    return orderLineItems.getLineItems();
  }

  public OrderState getOrderState() {
    return orderState;
  }

  public Restaurant getRestaurant() {
    return restaurant;
  }

  public Long getConsumerId() {
    return consumerId;
  }

  public void acceptTicket(LocalDateTime readyBy) {
    if (orderState == APPROVED) {
      this.acceptTime = LocalDateTime.now();
      if (!acceptTime.isBefore(readyBy))
        throw new IllegalArgumentException("readyBy is not in the future");
      this.readyBy = readyBy;
      this.orderState = ACCEPTED;
      return;
    }
    throw new UnsupportedStateTransitionException(orderState);
  }

  public void notePreparing() {
    switch (orderState) {
      case ACCEPTED:
        this.orderState = orderState.PREPARING;
        this.preparingTime = LocalDateTime.now();
        return;
      default:
        throw new UnsupportedStateTransitionException(orderState);
    }
  }

  public void noteReadyForPickup() {
    switch (orderState) {
      case PREPARING:
        this.orderState = OrderState.READY_FOR_PICKUP;
        this.readyForPickupTime = LocalDateTime.now();
        return;
      default:
        throw new UnsupportedStateTransitionException(orderState);
    }
  }

  public void notePickedUp() {
    switch (orderState) {
      case READY_FOR_PICKUP:
        this.orderState = OrderState.PICKED_UP;
        this.pickedUpTime = LocalDateTime.now();
        return;
      default:
        throw new UnsupportedStateTransitionException(orderState);
    }
  }

  public void schedule(Courier assignedCourier) {
    this.assignedCourier = assignedCourier;
  }

  public Courier getAssignedCourier() {
    return assignedCourier;
  }

  public void noteDelivered() {
    switch (orderState) {
      case PICKED_UP:
        this.orderState = OrderState.DELIVERED;
        this.deliveredTime = LocalDateTime.now();
        return;
      default:
        throw new UnsupportedStateTransitionException(orderState);
    }
  }
}

