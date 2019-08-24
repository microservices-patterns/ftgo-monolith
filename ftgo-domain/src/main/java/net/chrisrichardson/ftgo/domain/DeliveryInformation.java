package net.chrisrichardson.ftgo.domain;


import net.chrisrichardson.ftgo.common.Address;

import javax.persistence.*;
import java.time.LocalDateTime;

@Embeddable
@Access(AccessType.FIELD)
public class DeliveryInformation {

  private LocalDateTime deliveryTime;

  @Embedded
  @AttributeOverrides({
          @AttributeOverride(name="street1", column=@Column(name="delivery_address_street1")),
          @AttributeOverride(name="street2", column=@Column(name="delivery_address_street2")),
          @AttributeOverride(name="city", column=@Column(name="delivery_address_city")),
          @AttributeOverride(name="state", column=@Column(name="delivery_address_state")),
          @AttributeOverride(name="zip", column=@Column(name="delivery_address_zip")),
  })
  private Address deliveryAddress;
}


