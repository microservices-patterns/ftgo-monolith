use ftgo_delivery_service;

DROP table if exists delivery;

create table delivery
(
  id                       bigint not null auto_increment,
  order_state              varchar(255) NOT NULL,
  delivery_time            datetime,
  delivery_address_city    varchar(255) NOT NULL,
  delivery_address_state   varchar(255) NOT NULL,
  delivery_address_street1 varchar(255) NOT NULL,
  delivery_address_street2 varchar(255),
  delivery_address_zip     varchar(255) NOT NULL,
  assigned_courier_id      bigint,
  restaurant_id            bigint NOT NULL,
  picked_up_time           datetime,
  delivered_time           datetime,
  primary key (id)
) engine = InnoDB
  select id, order_state, delivery_time, restaurant_id, assigned_courier_id, delivered_time, picked_up_time, delivery_address_street1,
             delivery_address_street2, delivery_address_city, delivery_address_state, delivery_address_zip
  from ftgo.orders
  where assigned_courier_id is not null;


DELIMITER ;;

DROP TRIGGER IF EXISTS assigned_courier_id_updated;;

CREATE TRIGGER assigned_courier_id_updated
  AFTER UPDATE
  ON delivery
  FOR EACH ROW
BEGIN
  UPDATE ftgo.orders
  SET assigned_courier_id = NEW.assigned_courier_id
  where id = NEW.id;
END;;

