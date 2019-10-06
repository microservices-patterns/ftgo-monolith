use ftgo_delivery_service;

DROP TABLE IF exists courier;

create table courier
(
  id                       bigint not null auto_increment,
  available bit,
  first_name varchar(255),
  last_name varchar(255),
  primary key (id)
) engine = InnoDB
select id, available, first_name, last_name from ftgo.courier;


DROP TABLE IF exists courier_actions;

create table courier_actions
(
  courier_id bigint not null,
  order_id   bigint,
  time       datetime,
  type       varchar(255)
) engine = InnoDB
select * from ftgo.courier_actions;

DELIMITER ;;

DROP TRIGGER IF EXISTS ftgo_delivery_service.courier_availability_updated;;

CREATE TRIGGER courier_availability_updated
  AFTER UPDATE
  ON courier
  FOR EACH ROW
BEGIN
  IF NEW.available <> OLD.available THEN
    UPDATE ftgo.courier
    SET available = NEW.available
    where id = NEW.id;
  END IF;
END;;


DROP TRIGGER  IF EXISTS couriers_action_created
;;

CREATE TRIGGER courier_actions_created AFTER INSERT
  ON courier_actions
  FOR EACH ROW
BEGIN
  INSERT INTO ftgo.courier_actions(courier_id, order_id, time, type)
  VALUES (NEW.courier_id, NEW.order_id, NEW.time, NEW.type);
END;;


DELIMITER ;;

use ftgo;;

DROP TRIGGER  IF EXISTS courier_created
;;

CREATE TRIGGER courier_created AFTER INSERT
  ON courier
  FOR EACH ROW
BEGIN
  INSERT INTO ftgo_delivery_service.courier(id, first_name, last_name)
  VALUES (NEW.id, NEW.first_name, NEW.last_name);
END;;

DROP TRIGGER  IF EXISTS courier_updated
;;

CREATE TRIGGER courier_updated
  AFTER UPDATE
  ON courier
  FOR EACH ROW
BEGIN
  IF NEW.first_name <> OLD.first_name OR NEW.last_name <> OLD.last_name THEN
    UPDATE ftgo_delivery_service.courier
    SET first_name = NEW.first_name, last_name = NEW.last_name
    where id = NEW.id;
  END IF;
END;;


