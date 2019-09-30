package net.chrisrichardson.ftgo.deliveryservice.web;

import net.chrisrichardson.ftgo.deliveryservice.domain.DeliveryCourierService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class DeliveryController {

  private DeliveryCourierService deliveryCourierService;

  public DeliveryController(DeliveryCourierService deliveryCourierService) {
    this.deliveryCourierService = deliveryCourierService;
  }

  @RequestMapping(path="/orders/{orderId}/pickedup", method= RequestMethod.POST)
  public ResponseEntity<String> pickedup(@PathVariable long orderId) {
    deliveryCourierService.notePickedUp(orderId);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @RequestMapping(path="/orders/{orderId}/delivered", method= RequestMethod.POST)
  public ResponseEntity<String> delivered(@PathVariable long orderId) {
    deliveryCourierService.noteDelivered(orderId);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @RequestMapping(path="/couriers/{courierId}/availability", method= RequestMethod.POST)
  public ResponseEntity<String> updateCourierLocation(@PathVariable long courierId, @RequestBody CourierAvailability availability) {
    deliveryCourierService.updateAvailability(courierId, availability.isAvailable());
    return new ResponseEntity<>(HttpStatus.OK);
  }


}
