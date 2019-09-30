package net.chrisrichardson.ftgo.deliveryservice.domain;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DeliveryCourierRepository extends CrudRepository<DeliveryCourier, Long>, CustomDeliveryCourierRepository {

  @Query("SELECT c FROM DeliveryCourier c WHERE c.available = true")
  List<DeliveryCourier> findAllAvailable();

}
