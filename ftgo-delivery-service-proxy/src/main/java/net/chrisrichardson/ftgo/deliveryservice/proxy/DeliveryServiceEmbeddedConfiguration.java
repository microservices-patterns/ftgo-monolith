package net.chrisrichardson.ftgo.deliveryservice.proxy;

import net.chrisrichardson.ftgo.deliveryservice.web.DeliveryServiceWebConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

@Configuration
@Import(DeliveryServiceWebConfiguration.class)
@Profile("!RemoteDeliveryService")
public class DeliveryServiceEmbeddedConfiguration {
}
