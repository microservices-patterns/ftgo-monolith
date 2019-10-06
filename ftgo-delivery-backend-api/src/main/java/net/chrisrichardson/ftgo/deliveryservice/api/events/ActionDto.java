package net.chrisrichardson.ftgo.deliveryservice.api.events;

import java.time.LocalDateTime;

public class ActionDto {
  private String type;
  private LocalDateTime time;

  public ActionDto() {
  }

  public ActionDto(String type, LocalDateTime time) {
    this.type = type;
    this.time = time;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public LocalDateTime getTime() {
    return time;
  }

  public void setTime(LocalDateTime time) {
    this.time = time;
  }
}
