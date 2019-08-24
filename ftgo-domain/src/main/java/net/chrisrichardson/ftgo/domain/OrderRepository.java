package net.chrisrichardson.ftgo.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Long> {
  List<Order> findAllByConsumerId(Long consumerId);
}
