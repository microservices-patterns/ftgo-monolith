package net.chrisrichardson.ftgo.restaurantservice.events;

import io.eventuate.tram.events.common.DomainEvent;
import net.chrisrichardson.ftgo.common.RestaurantMenu;

public class RestaurantMenuRevised implements DomainEvent {

  private RestaurantMenu menu;

  public RestaurantMenu getRevisedMenu() {
    return menu;
  }
}
