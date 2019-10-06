FROM mysql:5.7.13
COPY replication.cnf /etc/mysql/conf.d
COPY schema.sql /docker-entrypoint-initdb.d/schema.sql
