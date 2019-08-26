package net.chrisrichardson.ftgo.courierservice.api;

public class CreateCourierResponse {
  private long id;

  public CreateCourierResponse() {
  }

  public CreateCourierResponse(long id) {
    this.id = id;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }
}
