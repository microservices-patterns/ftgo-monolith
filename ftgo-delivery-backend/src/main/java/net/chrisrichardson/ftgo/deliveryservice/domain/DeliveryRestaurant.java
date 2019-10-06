package net.chrisrichardson.ftgo.deliveryservice.domain;

import net.chrisrichardson.ftgo.common.Address;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Table(name = "restaurants", catalog = "ftgo_delivery_service")
@Access(AccessType.FIELD)
@DynamicUpdate
public class DeliveryRestaurant {

  @Id
  private Long id;

  private String name;

  @Embedded
  private Address address;


  public DeliveryRestaurant() {
  }

  public DeliveryRestaurant(long id, String name, Address address) {
    this.id = id;
    this.name = name;
    this.address = address;
  }
}
