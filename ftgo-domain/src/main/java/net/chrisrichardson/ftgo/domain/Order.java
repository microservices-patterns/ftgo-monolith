package net.chrisrichardson.ftgo.domain;

import net.chrisrichardson.ftgo.common.Money;
import net.chrisrichardson.ftgo.common.UnsupportedStateTransitionException;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

import static net.chrisrichardson.ftgo.domain.OrderState.*;
import static net.chrisrichardson.ftgo.domain.TicketState.ACCEPTED;
import static net.chrisrichardson.ftgo.domain.TicketState.AWAITING_ACCEPTANCE;


@Entity
@Table(name = "orders")
@Access(AccessType.FIELD)
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
  private Money orderMinimum = new Money(Integer.MAX_VALUE);

  @Enumerated(EnumType.STRING)
  private TicketState ticketState = AWAITING_ACCEPTANCE;

  private TicketState previousTicketState;

  private LocalDateTime readyBy;
  private LocalDateTime acceptTime;
  private LocalDateTime preparingTime;
  private LocalDateTime pickedUpTime;
  private LocalDateTime readyForPickupTime;

  @ManyToOne
  private Courier assignedCourier;

  @Enumerated(EnumType.STRING)
  private DeliveryState deliveryState = DeliveryState.PENDING;

  private Order() {
  }

  public Order(long consumerId, Restaurant restaurant, List<OrderLineItem> orderLineItems) {
    this.consumerId = consumerId;
    this.restaurant = restaurant;
    this.orderLineItems = new OrderLineItems(orderLineItems);
    this.orderState = APPROVAL_PENDING;
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

    switch (ticketState) {
      case AWAITING_ACCEPTANCE:
      case ACCEPTED:
        break;
      default:
        throw new UnsupportedStateTransitionException(ticketState);
    }

    this.ticketState = TicketState.CANCELLED;

    this.orderState = CANCELLED;

  }

  public void noteApproved() {
    switch (orderState) {
      case APPROVAL_PENDING:
        this.orderState = APPROVED;
        return;
      default:
        throw new UnsupportedStateTransitionException(orderState);
    }

  }

  public void noteRejected() {
    switch (orderState) {
      case APPROVAL_PENDING:
        this.orderState = REJECTED;
        return;
      default:
        throw new UnsupportedStateTransitionException(orderState);
    }

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

    if (this.ticketState != AWAITING_ACCEPTANCE && this.ticketState != ACCEPTED) {
      throw new UnsupportedStateTransitionException(this.ticketState);
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
    if (ticketState == AWAITING_ACCEPTANCE) {
      this.acceptTime = LocalDateTime.now();
      if (!acceptTime.isBefore(readyBy))
        throw new IllegalArgumentException("readyBy is not in the future");
      this.readyBy = readyBy;
      this.ticketState = ACCEPTED;
      return;
    }
    throw new UnsupportedStateTransitionException(ticketState);
  }

  public void preparingTicket() {
    switch (ticketState) {
      case ACCEPTED:
        this.ticketState = TicketState.PREPARING;
        this.preparingTime = LocalDateTime.now();
        return;
      default:
        throw new UnsupportedStateTransitionException(ticketState);
    }
  }

  public void ticketReadyForPickup() {
    switch (ticketState) {
      case PREPARING:
        this.ticketState = TicketState.READY_FOR_PICKUP;
        this.readyForPickupTime = LocalDateTime.now();
        return;
      default:
        throw new UnsupportedStateTransitionException(ticketState);
    }
  }

  public void ticketPickedUp() {
    switch (ticketState) {
      case READY_FOR_PICKUP:
        this.ticketState = TicketState.PICKED_UP;
        this.pickedUpTime = LocalDateTime.now();
        return;
      default:
        throw new UnsupportedStateTransitionException(ticketState);
    }
  }

  public void schedule(LocalDateTime readyBy, Courier assignedCourier) {
    if (deliveryState == DeliveryState.PENDING) {
      this.assignedCourier = assignedCourier;
      this.deliveryState = DeliveryState.SCHEDULED;
    } else
      throw new UnsupportedStateTransitionException(deliveryState);
  }

  public Courier getAssignedCourier() {
    return assignedCourier;
  }
}

