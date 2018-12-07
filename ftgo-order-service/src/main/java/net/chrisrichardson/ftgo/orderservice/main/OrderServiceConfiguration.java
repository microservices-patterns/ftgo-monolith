package net.chrisrichardson.ftgo.orderservice.main;

import io.eventuate.jdbckafka.TramJdbcKafkaConfiguration;
import net.chrisrichardson.eventstore.examples.customersandorders.commonswagger.CommonSwaggerConfiguration;
import net.chrisrichardson.ftgo.orderservice.domain.OrderConfiguration;
import net.chrisrichardson.ftgo.orderservice.grpc.GrpcConfiguration;
import net.chrisrichardson.ftgo.orderservice.messaging.OrderServiceMessagingConfiguration;
import net.chrisrichardson.ftgo.orderservice.web.OrderWebConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableAutoConfiguration
@ComponentScan
@EntityScan
@Import({OrderConfiguration.class, OrderWebConfiguration.class, OrderServiceMessagingConfiguration.class,
        TramJdbcKafkaConfiguration.class, CommonSwaggerConfiguration.class, GrpcConfiguration.class})
public class OrderServiceConfiguration {
}
