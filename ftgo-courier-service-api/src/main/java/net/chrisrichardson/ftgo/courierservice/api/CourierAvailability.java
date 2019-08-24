package net.chrisrichardson.ftgo.courierservice.api;

public class CourierAvailability {

  private boolean available;

  public CourierAvailability() {
  }

  public CourierAvailability(boolean available) {
    this.available = available;
  }

  public boolean isAvailable() {
    return available;
  }

  public void setAvailable(boolean available) {
    this.available = available;
  }
}
