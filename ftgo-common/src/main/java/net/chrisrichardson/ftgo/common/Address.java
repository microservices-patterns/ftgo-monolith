package net.chrisrichardson.ftgo.common;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;

@Embeddable
@Access(AccessType.FIELD)
public class Address {

  private String street1;
  private String street2;
  private String city;
  private String state;
  private String zip;

}
