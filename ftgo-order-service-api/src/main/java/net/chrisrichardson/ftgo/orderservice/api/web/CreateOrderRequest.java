package net.chrisrichardson.ftgo.orderservice.api.web;

import net.chrisrichardson.ftgo.common.Address;

import java.time.LocalDateTime;
import java.util.List;

public class CreateOrderRequest {

  private long restaurantId;
  private long consumerId;
  private List<LineItem> lineItems;
  private LocalDateTime deliveryTime;
  private Address deliveryAddress;

  public CreateOrderRequest(long consumerId, long restaurantId, LocalDateTime deliveryTime, Address deliveryAddress, List<LineItem> lineItems) {
    this.restaurantId = restaurantId;
    this.consumerId = consumerId;
    this.deliveryTime = deliveryTime;
    this.deliveryAddress = deliveryAddress;
    this.lineItems = lineItems;

  }

  private CreateOrderRequest() {
  }

  public long getRestaurantId() {
    return restaurantId;
  }

  public void setRestaurantId(long restaurantId) {
    this.restaurantId = restaurantId;
  }

  public long getConsumerId() {
    return consumerId;
  }

  public void setConsumerId(long consumerId) {
    this.consumerId = consumerId;
  }

  public List<LineItem> getLineItems() {
    return lineItems;
  }

  public void setLineItems(List<LineItem> lineItems) {
    this.lineItems = lineItems;
  }

  public LocalDateTime getDeliveryTime() {
    return deliveryTime;
  }

  public Address getDeliveryAddress() {
    return deliveryAddress;
  }

  public static class LineItem {

    private String menuItemId;
    private int quantity;

    private LineItem() {
    }

    public LineItem(String menuItemId, int quantity) {
      this.menuItemId = menuItemId;

      this.quantity = quantity;
    }

    public String getMenuItemId() {
      return menuItemId;
    }

    public int getQuantity() {
      return quantity;
    }

    public void setQuantity(int quantity) {
      this.quantity = quantity;
    }

    public void setMenuItemId(String menuItemId) {
      this.menuItemId = menuItemId;

    }

  }

  public void setDeliveryTime(LocalDateTime deliveryTime) {
    this.deliveryTime = deliveryTime;
  }

  public void setDeliveryAddress(Address deliveryAddress) {
    this.deliveryAddress = deliveryAddress;
  }
}
