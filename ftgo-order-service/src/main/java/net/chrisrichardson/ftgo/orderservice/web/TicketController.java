package net.chrisrichardson.ftgo.orderservice.web;

import net.chrisrichardson.ftgo.orderservice.api.web.OrderAcceptance;
import net.chrisrichardson.ftgo.orderservice.domain.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TicketController {

  private OrderService orderService;

  public TicketController(OrderService orderService) {
    this.orderService = orderService;
  }


}
