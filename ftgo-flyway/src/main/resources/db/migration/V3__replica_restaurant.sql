use ftgo_delivery_service;

DROP TABLE IF exists restaurants;

create table restaurants
(
  id   bigint not null auto_increment,
  name varchar(255),
  street1 varchar(255) NOT NULL,
  street2 varchar(255),
  city    varchar(255) NOT NULL,
  state   varchar(255) NOT NULL,
  zip     varchar(255) NOT NULL,
  primary key (id)
) engine = InnoDB
select id, name, street1, street2, city, state, zip from ftgo.restaurants;

use ftgo;

DROP trigger if exists restaurant_created;

DELIMITER ;;

CREATE TRIGGER restaurant_created AFTER INSERT
  ON restaurants
  FOR EACH ROW
BEGIN
  INSERT INTO ftgo_delivery_service.restaurants(name, street1, street2, city, state, zip)
  VALUES (NEW.name, NEW.street1, NEW.street2, NEW.city, NEW.state, NEW.zip);
END;;




