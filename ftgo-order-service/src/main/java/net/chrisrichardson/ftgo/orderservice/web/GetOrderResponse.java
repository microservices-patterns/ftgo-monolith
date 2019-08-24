package net.chrisrichardson.ftgo.orderservice.web;

import net.chrisrichardson.ftgo.common.Money;

public class GetOrderResponse {
  private long orderId;
  private String state;
  private Money orderTotal;
  private String restaurantName;
  private Long assignedCourier;

  private GetOrderResponse() {
  }

  public Long getAssignedCourier() {
    return assignedCourier;
  }

  public void setAssignedCourier(Long assignedCourier) {
    this.assignedCourier = assignedCourier;
  }

  public GetOrderResponse(long orderId, String state, Money orderTotal, String restaurantName, Long assignedCourier) {
    this.orderId = orderId;
    this.state = state;
    this.orderTotal = orderTotal;
    this.restaurantName = restaurantName;
    this.assignedCourier = assignedCourier;
  }

  public Money getOrderTotal() {
    return orderTotal;
  }

  public void setOrderTotal(Money orderTotal) {
    this.orderTotal = orderTotal;
  }

  public long getOrderId() {
    return orderId;
  }

  public void setOrderId(long orderId) {
    this.orderId = orderId;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public String getRestaurantName() {
    return restaurantName;
  }
}
