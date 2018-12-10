package net.chrisrichardson.ftgo.kitchenservice.domain;

import net.chrisrichardson.ftgo.common.NotYetImplementedException;
import net.chrisrichardson.ftgo.common.UnsupportedStateTransitionException;
import net.chrisrichardson.ftgo.kitchenservice.api.TicketDetails;
import net.chrisrichardson.ftgo.kitchenservice.api.TicketLineItem;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "tickets")
@Access(AccessType.FIELD)
public class Ticket {

  @Id
  private Long id;

  @Enumerated(EnumType.STRING)
  private TicketState state;

  private TicketState previousState;

  private Long restaurantId;

  @ElementCollection
  @CollectionTable(name = "ticket_line_items")
  private List<TicketLineItem> lineItems;

  private LocalDateTime readyBy;
  private LocalDateTime acceptTime;
  private LocalDateTime preparingTime;
  private LocalDateTime pickedUpTime;
  private LocalDateTime readyForPickupTime;

  private Ticket() {
  }

  public Ticket(long restaurantId, Long id, TicketDetails details) {
    this.restaurantId = restaurantId;
    this.id = id;
    this.state = TicketState.CREATE_PENDING;
    this.lineItems = details.getLineItems();
  }

  public void confirmCreate() {
    switch (state) {
      case CREATE_PENDING:
        state = TicketState.AWAITING_ACCEPTANCE;
      return;
      default:
        throw new UnsupportedStateTransitionException(state);
    }
  }

  public void cancelCreate() {
    throw new NotYetImplementedException();
  }


  public void accept(LocalDateTime readyBy) {
    switch (state) {
      case AWAITING_ACCEPTANCE:
        // Verify that readyBy is in the futurestate = TicketState.ACCEPTED;
        this.acceptTime = LocalDateTime.now();
        if (!acceptTime.isBefore(readyBy))
          throw new IllegalArgumentException("readyBy is not in the future");
        this.readyBy = readyBy;
        return;
      default:
        throw new UnsupportedStateTransitionException(state);
    }
  }

  // TODO reject()

  // TODO cancel()

  public void preparing() {
    switch (state) {
      case ACCEPTED:
        this.state = TicketState.PREPARING;
        this.preparingTime = LocalDateTime.now();
        return;
      default:
        throw new UnsupportedStateTransitionException(state);
    }
  }

  public void readyForPickup() {
    switch (state) {
      case PREPARING:
        this.state = TicketState.READY_FOR_PICKUP;
        this.readyForPickupTime = LocalDateTime.now();
        return;
      default:
        throw new UnsupportedStateTransitionException(state);
    }
  }

  public void pickedUp() {
    switch (state) {
      case READY_FOR_PICKUP:
        this.state = TicketState.PICKED_UP;
        this.pickedUpTime = LocalDateTime.now();
        return;
      default:
        throw new UnsupportedStateTransitionException(state);
    }
  }

  public void changeLineItemQuantity() {
    switch (state) {
      case AWAITING_ACCEPTANCE:
        // TODO
        break;
      case PREPARING:
        // TODO - too late
        break;
      default:
        throw new UnsupportedStateTransitionException(state);
    }

  }

  public void cancel() {
    switch (state) {
      case AWAITING_ACCEPTANCE:
      case ACCEPTED:
        this.previousState = state;
        this.state = TicketState.CANCEL_PENDING;
        return;
      default:
        throw new UnsupportedStateTransitionException(state);
    }
  }

  public Long getId() {
    return id;
  }

  public void confirmCancel() {
    switch (state) {
      case CANCEL_PENDING:
        this.state = TicketState.CANCELLED;
        return;
      default:
        throw new UnsupportedStateTransitionException(state);

    }
  }
  public void undoCancel() {
    switch (state) {
      case CANCEL_PENDING:
        this.state = this.previousState;
        return;
      default:
        throw new UnsupportedStateTransitionException(state);

    }
  }

  public void beginReviseOrder(Map<String, Integer> revisedLineItemQuantities) {
    switch (state) {
      case AWAITING_ACCEPTANCE:
      case ACCEPTED:
        this.previousState = state;
        this.state = TicketState.REVISION_PENDING;
        return;
      default:
        throw new UnsupportedStateTransitionException(state);
    }
  }

  public void undoBeginReviseOrder() {
    switch (state) {
      case REVISION_PENDING:
        this.state = this.previousState;
        return;
      default:
        throw new UnsupportedStateTransitionException(state);
    }
  }

  public void confirmReviseTicket(Map<String, Integer> revisedLineItemQuantities) {
    switch (state) {
      case REVISION_PENDING:
        this.state = this.previousState;
        return;
      default:
        throw new UnsupportedStateTransitionException(state);

    }
  }
}
