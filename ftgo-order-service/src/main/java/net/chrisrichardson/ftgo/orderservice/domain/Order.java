package net.chrisrichardson.ftgo.orderservice.domain;

import net.chrisrichardson.ftgo.common.Money;
import net.chrisrichardson.ftgo.common.NotYetImplementedException;
import net.chrisrichardson.ftgo.common.Restaurant;
import net.chrisrichardson.ftgo.common.UnsupportedStateTransitionException;
import net.chrisrichardson.ftgo.orderservice.api.events.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static net.chrisrichardson.ftgo.orderservice.api.events.OrderState.APPROVED;
import static net.chrisrichardson.ftgo.orderservice.api.events.OrderState.APPROVAL_PENDING;
import static net.chrisrichardson.ftgo.orderservice.api.events.OrderState.REJECTED;
import static net.chrisrichardson.ftgo.orderservice.api.events.OrderState.REVISION_PENDING;

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

  @OneToOne(fetch = FetchType.LAZY)
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
  private TicketState ticketState = TicketState.CREATE_PENDING;

  private TicketState previousTicketState;

  private LocalDateTime readyBy;
  private LocalDateTime acceptTime;
  private LocalDateTime preparingTime;
  private LocalDateTime pickedUpTime;
  private LocalDateTime readyForPickupTime;

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
    switch (orderState) {
      case APPROVED:
        this.orderState = OrderState.CANCEL_PENDING;
        return;
      default:
        throw new UnsupportedStateTransitionException(orderState);
    }
  }

  public void undoPendingCancel() {
    switch (orderState) {
      case CANCEL_PENDING:
        this.orderState = OrderState.APPROVED;
        return;
      default:
        throw new UnsupportedStateTransitionException(orderState);
    }
  }

  public void noteCancelled() {
    switch (orderState) {
      case CANCEL_PENDING:
        this.orderState = OrderState.CANCELLED;
        return;
      default:
        throw new UnsupportedStateTransitionException(orderState);
    }
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

  public LineItemQuantityChange revise(OrderRevision orderRevision) {
    switch (orderState) {

      case APPROVED:
        LineItemQuantityChange change = orderLineItems.lineItemQuantityChange(orderRevision);
        if (change.newOrderTotal.isGreaterThanOrEqual(orderMinimum)) {
          throw new OrderMinimumNotMetException();
        }
        this.orderState = REVISION_PENDING;
        return change;

      default:
        throw new UnsupportedStateTransitionException(orderState);
    }
  }

  public void rejectRevision() {
    switch (orderState) {
      case REVISION_PENDING:
        this.orderState = APPROVED;
        return;
      default:
        throw new UnsupportedStateTransitionException(orderState);
    }
  }

  public void confirmRevision(OrderRevision orderRevision) {
    switch (orderState) {
      case REVISION_PENDING:
        LineItemQuantityChange licd = orderLineItems.lineItemQuantityChange(orderRevision);

        orderRevision.getDeliveryInformation().ifPresent(newDi -> this.deliveryInformation = newDi);

        if (!orderRevision.getRevisedLineItemQuantities().isEmpty()) {
          orderLineItems.updateLineItems(orderRevision);
        }

        this.orderState = APPROVED;
        return;
      default:
        throw new UnsupportedStateTransitionException(orderState);
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

  public void confirmCreateTicket() {
    switch (ticketState) {
      case CREATE_PENDING:
        ticketState = TicketState.AWAITING_ACCEPTANCE;
        return;
      default:
        throw new UnsupportedStateTransitionException(ticketState);
    }
  }

  public void cancelCreateTicket() {
    throw new NotYetImplementedException();
  }

  public void acceptTicket(LocalDateTime readyBy) {
    switch (ticketState) {
      case AWAITING_ACCEPTANCE:
        // Verify that readyBy is in the futurestate = TicketState.ACCEPTED;
        this.acceptTime = LocalDateTime.now();
        if (!acceptTime.isBefore(readyBy))
          throw new IllegalArgumentException("readyBy is not in the future");
        this.readyBy = readyBy;
        return;
      default:
        throw new UnsupportedStateTransitionException(ticketState);
    }
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

  public void cancelTicket() {
    switch (ticketState) {
      case AWAITING_ACCEPTANCE:
      case ACCEPTED:
        this.previousTicketState = ticketState;
        this.ticketState = TicketState.CANCEL_PENDING;
        return;
      default:
        throw new UnsupportedStateTransitionException(ticketState);
    }
  }

  public void confirmCancelTicket() {
    switch (ticketState) {
      case CANCEL_PENDING:
        this.ticketState = TicketState.CANCELLED;
        return;
      default:
        throw new UnsupportedStateTransitionException(ticketState);
    }
  }

  public void undoCancelTicket() {
    switch (ticketState) {
      case CANCEL_PENDING:
        this.ticketState = this.previousTicketState;
        return;
      default:
        throw new UnsupportedStateTransitionException(ticketState);

    }
  }

  public void beginReviseTicket(Map<String, Integer> revisedLineItemQuantities) {
    switch (ticketState) {
      case AWAITING_ACCEPTANCE:
      case ACCEPTED:
        this.previousTicketState = ticketState;
        this.ticketState = TicketState.REVISION_PENDING;
        return;
      default:
        throw new UnsupportedStateTransitionException(ticketState);
    }
  }

  public void undoBeginReviseTicket() {
    switch (ticketState) {
      case REVISION_PENDING:
        this.ticketState = this.previousTicketState;
        return;
      default:
        throw new UnsupportedStateTransitionException(ticketState);
    }
  }

  public void confirmReviseTicket(Map<String, Integer> revisedLineItemQuantities) {
    switch (ticketState) {
      case REVISION_PENDING:
        this.ticketState = this.previousTicketState;
        return;
      default:
        throw new UnsupportedStateTransitionException(ticketState);
    }
  }
}

