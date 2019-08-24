package net.chrisrichardson.ftgo.restaurantservice.events;


import net.chrisrichardson.ftgo.common.Address;

public class CreateRestaurantRequest {

  private String name;
  private RestaurantMenuDTO menu;
  private Address address;

  public CreateRestaurantRequest(String name, Address address, RestaurantMenuDTO menu) {
    this.name = name;
    this.address = address;
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

  public void setAddress(Address address) {
    this.address = address;
  }

  public Address getAddress() {
    return address;
  }
}
