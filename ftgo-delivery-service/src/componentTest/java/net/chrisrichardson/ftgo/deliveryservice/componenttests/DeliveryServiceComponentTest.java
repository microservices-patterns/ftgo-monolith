package net.chrisrichardson.ftgo.deliveryservice.componenttests;

import io.eventuate.tram.commands.consumer.TramCommandConsumerConfiguration;
import io.eventuate.tram.commands.producer.CommandProducer;
import io.eventuate.tram.commands.producer.TramCommandProducerConfiguration;
import io.eventuate.tram.events.common.EventMessageHeaders;
import io.eventuate.tram.jdbckafka.TramJdbcKafkaConfiguration;
import io.eventuate.tram.messaging.consumer.MessageConsumer;
import net.chrisrichardson.ftgo.common.Address;
import net.chrisrichardson.ftgo.deliveryservice.api.messaging.DeliveryServiceChannels;
import net.chrisrichardson.ftgo.deliveryservice.api.messaging.ScheduleDelivery;
import net.chrisrichardson.ftgo.deliveryservice.domain.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.support.TransactionTemplate;

import java.time.LocalDateTime;
import java.util.Collections;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=DeliveryServiceComponentTest.Config.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class DeliveryServiceComponentTest {

  private long courierId;
  private long restaurantId;
  private long orderId;

  private static final Address RESTAURANT_ADDRESS = new Address("1 High Street", null, "Oakland", "CA", "94619");

  private static final Address DELIVERY_ADDRESS = new Address("99 Scenic Drive", null, "Oakland", "CA", "94620");
  public static final String AJANTA_RESTAURANT_NAME = "Ajanta";


  @Configuration
  @Import({TramCommandConsumerConfiguration.class, TramJdbcKafkaConfiguration.class, DeliveryServiceDomainConfiguration.class, TramCommandProducerConfiguration.class})
  @EnableAutoConfiguration
  public static class Config {

    @Bean
    public MessageTracker messageTracker(MessageConsumer messageConsumer) {
      return new MessageTracker(Collections.singleton(net.chrisrichardson.ftgo.deliveryservice.api.events.DeliveryServiceChannels.COURIER_CHANNEL), messageConsumer);
    }
  }

  @Autowired
  private DeliveryCourierRepository deliveryCourierRepository;

  @Autowired
  private DeliveryRestaurantRepository deliveryRestaurantRepository;

  @Autowired
  private CommandProducer commandProducer;

  @Autowired
  private MessageTracker messageTracker;

  @Autowired
  private TransactionTemplate transactionTemplate;

  @Before
  public void setUp() {
    transactionTemplate.execute(ts -> {
      deliveryCourierRepository.findAll().forEach(DeliveryCourier::noteUnavailable);
      return null;
    });
  }

  @Test
  public void shouldScheduleADelivery() {

    createCourier();

    createRestaurant();

    createDelivery();

    assertCourierAssignedToDelivery();

  }

  private void createCourier() {
    courierId = System.currentTimeMillis();
    deliveryCourierRepository.save(new DeliveryCourier(courierId, true));
  }

  private void createRestaurant() {
    restaurantId = System.currentTimeMillis();
    deliveryRestaurantRepository.save(new DeliveryRestaurant(restaurantId, AJANTA_RESTAURANT_NAME, RESTAURANT_ADDRESS));
  }

  private void createDelivery() {
    orderId = System.currentTimeMillis();

    commandProducer.send(DeliveryServiceChannels.DELIVERY_SERVICE_CHANNEL, new ScheduleDelivery(LocalDateTime.now(), orderId, restaurantId, DELIVERY_ADDRESS), "replyTo-dont-care",
            Collections.emptyMap());

  }

  private void assertCourierAssignedToDelivery() {
    messageTracker.assertDomainEventPublished(net.chrisrichardson.ftgo.deliveryservice.api.events.DeliveryServiceChannels.COURIER_CHANNEL,
            m -> m.getRequiredHeader(EventMessageHeaders.AGGREGATE_ID).equals(Long.toString(courierId)));
  }
}
