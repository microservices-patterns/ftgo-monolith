create database ftgo;
GRANT ALL PRIVILEGES ON ftgo.* TO 'mysqluser'@'%' WITH GRANT OPTION;

create database ftgo_delivery_service;
GRANT ALL PRIVILEGES ON ftgo_delivery_service.* TO 'mysqluser'@'%' WITH GRANT OPTION;

create database eventuate;
GRANT ALL PRIVILEGES ON eventuate.* TO 'mysqluser'@'%' WITH GRANT OPTION;
