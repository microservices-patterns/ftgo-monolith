package net.chrisrichardson.ftgo.kitchenservice.domain;

import net.chrisrichardson.ftgo.common.Restaurant;
import net.chrisrichardson.ftgo.common.RestaurantRepository;
import net.chrisrichardson.ftgo.kitchenservice.api.TicketDetails;
import net.chrisrichardson.ftgo.common.RestaurantMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;

@Transactional
public class KitchenService {

  @Autowired
  private TicketRepository ticketRepository;

  @Autowired
  private RestaurantRepository restaurantRepository;

  public void createMenu(long id, RestaurantMenu menu) {
    Restaurant restaurant = new Restaurant(id, null, menu);
    restaurantRepository.save(restaurant);
  }

  public void reviseMenu(long ticketId, RestaurantMenu revisedMenu) {
    Restaurant restaurant = restaurantRepository.findById(ticketId)
            .orElseThrow(() -> new TicketNotFoundException(ticketId));
    restaurant.reviseMenu(revisedMenu);
  }

  public Ticket createTicket(long restaurantId, Long ticketId, TicketDetails ticketDetails) {
    Ticket ticket = new Ticket(restaurantId, ticketId, ticketDetails);
    ticketRepository.save(ticket);
    return ticket;
  }

  public void accept(long ticketId, LocalDateTime readyBy) {
    Ticket ticket = ticketRepository.findById(ticketId)
            .orElseThrow(() -> new TicketNotFoundException(ticketId));
    ticket.accept(readyBy);
  }

  public void confirmCreateTicket(Long ticketId) {
    Ticket ro = ticketRepository.findById(ticketId)
            .orElseThrow(() -> new TicketNotFoundException(ticketId));
    ro.confirmCreate();
  }

  public void cancelCreateTicket(Long ticketId) {
    Ticket ro = ticketRepository.findById(ticketId)
            .orElseThrow(() -> new TicketNotFoundException(ticketId));
    ro.cancelCreate();
  }


  public void cancelTicket(long restaurantId, long ticketId) {
    Ticket ticket = ticketRepository.findById(ticketId)
            .orElseThrow(() -> new TicketNotFoundException(ticketId));
    // TODO - verify restaurant id
    ticket.cancel();
  }


  public void confirmCancelTicket(long restaurantId, long ticketId) {
    Ticket ticket = ticketRepository.findById(ticketId)
            .orElseThrow(() -> new TicketNotFoundException(ticketId));
    // TODO - verify restaurant id
    ticket.confirmCancel();
  }

  public void undoCancel(long restaurantId, long ticketId) {
    Ticket ticket = ticketRepository.findById(ticketId)
            .orElseThrow(() -> new TicketNotFoundException(ticketId));
    // TODO - verify restaurant id
    ticket.undoCancel();

  }

  public void beginReviseOrder(long restaurantId, Long ticketId, Map<String, Integer> revisedLineItemQuantities) {
    Ticket ticket = ticketRepository.findById(ticketId)
            .orElseThrow(() -> new TicketNotFoundException(ticketId));
    // TODO - verify restaurant id
    ticket.beginReviseOrder(revisedLineItemQuantities);
  }

  public void undoBeginReviseOrder(long restaurantId, Long ticketId) {
    Ticket ticket = ticketRepository.findById(ticketId)
            .orElseThrow(() -> new TicketNotFoundException(ticketId));
    // TODO - verify restaurant id
    ticket.undoBeginReviseOrder();
  }

  public void confirmReviseTicket(long restaurantId, long ticketId, Map<String, Integer> revisedLineItemQuantities) {
    Ticket ticket = ticketRepository.findById(ticketId)
            .orElseThrow(() -> new TicketNotFoundException(ticketId));
    // TODO - verify restaurant id
    ticket.confirmReviseTicket(revisedLineItemQuantities);
  }
}
