package net.chrisrichardson.ftgo.restaurantservice.events;


public class CreateRestaurantRequest {

  private String name;
  private RestaurantMenuDTO menu;

  public CreateRestaurantRequest(String name, RestaurantMenuDTO menu) {
    this.name = name;
    this.menu = menu;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public RestaurantMenuDTO getMenu() {
    return menu;
  }

  public void setMenu(RestaurantMenuDTO menu) {
    this.menu = menu;
  }

  private CreateRestaurantRequest() {

  }
}
