use ftgo_delivery_service;

alter table courier_actions
  add constraint courier_actions_order_id foreign key (order_id) references delivery (id);

alter table courier_actions
  add constraint courier_actions_courier_id foreign key (courier_id) references courier (id);

alter table delivery
  add constraint delivery_assigned_courier_id foreign key (assigned_courier_id) references courier (id);

alter table delivery
  add constraint delivery_restaurant_id foreign key (restaurant_id) references restaurants (id);

