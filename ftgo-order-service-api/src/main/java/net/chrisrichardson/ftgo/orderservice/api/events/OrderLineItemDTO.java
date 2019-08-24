package net.chrisrichardson.ftgo.orderservice.api.events;

public class OrderLineItemDTO {

  private int quantity;
  private String menuItemId;
  private String name;

  public OrderLineItemDTO() {
  }


  public OrderLineItemDTO(int quantity, String menuItemId, String name) {
    this.quantity = quantity;
    this.menuItemId = menuItemId;
    this.name = name;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public String getMenuItemId() {
    return menuItemId;
  }

  public void setMenuItemId(String menuItemId) {
    this.menuItemId = menuItemId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
