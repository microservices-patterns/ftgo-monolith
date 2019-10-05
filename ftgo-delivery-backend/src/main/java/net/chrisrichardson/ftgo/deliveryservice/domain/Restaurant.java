package net.chrisrichardson.ftgo.deliveryservice.domain;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Table(name = "restaurant")
@Access(AccessType.FIELD)
@DynamicUpdate
public class Restaurant {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

}
