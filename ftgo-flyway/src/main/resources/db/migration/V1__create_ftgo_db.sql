use ftgo;

create table consumers
(
  id         bigint not null,
  first_name varchar(255),
  last_name  varchar(255),
  primary key (id)
) engine = InnoDB;

create table courier
(
  id                       bigint not null auto_increment,
  available bit,
  first_name varchar(255),
  last_name varchar(255),
  street1 varchar(255),
  street2 varchar(255),
  city    varchar(255),
  state   varchar(255),
  zip     varchar(255),
  primary key (id)
) engine = InnoDB;

create table courier_actions
(
  courier_id bigint not null,
  order_id   bigint,
  time       datetime,
  type       varchar(255)
) engine = InnoDB;

create table hibernate_sequence
(
  next_val bigint
) engine = InnoDB;

insert into hibernate_sequence
values (1);

create table order_line_items
(
  order_id     bigint  not null,
  menu_item_id varchar(255),
  name         varchar(255),
  price        decimal(19, 2),
  quantity     integer not null
) engine = InnoDB;

create table orders
(
  id                       bigint not null auto_increment,
  accept_time              datetime,
  consumer_id              bigint,
  delivery_address_city    varchar(255),
  delivery_address_state   varchar(255),
  delivery_address_street1 varchar(255),
  delivery_address_street2 varchar(255),
  delivery_address_zip     varchar(255),
  delivery_time            datetime,
  order_state              varchar(255),
  order_minimum            decimal(19, 2),
  payment_token            varchar(255),
  picked_up_time           datetime,
  delivered_time           datetime,
  preparing_time           datetime,
  previous_ticket_state    integer,
  ready_by                 datetime,
  ready_for_pickup_time    datetime,
  version                  bigint,
  assigned_courier_id      bigint,
  restaurant_id            bigint,
  primary key (id)
) engine = InnoDB;

create table restaurant_menu_items
(
  restaurant_id bigint not null,
  id            varchar(255),
  name          varchar(255),
  price        decimal(19, 2)
) engine = InnoDB;

create table restaurants
(
  id   bigint not null auto_increment,
  name varchar(255),
  street1 varchar(255),
  street2 varchar(255),
  city    varchar(255),
  state   varchar(255),
  zip     varchar(255),
  primary key (id)
) engine = InnoDB;

alter table courier_actions
  add constraint courier_actions_order_id foreign key (order_id) references orders (id);

alter table courier_actions
  add constraint courier_actions_courier_id foreign key (courier_id) references courier (id);

alter table order_line_items
  add constraint order_line_items_id foreign key (order_id) references orders (id);

alter table orders
  add constraint orders_assigned_courier_id foreign key (assigned_courier_id) references courier (id);

alter table orders
  add constraint orders_restaurant_id foreign key (restaurant_id) references restaurants (id);

alter table restaurant_menu_items
  add constraint restaurant_menu_items_restaurant_id foreign key (restaurant_id) references restaurants (id);
