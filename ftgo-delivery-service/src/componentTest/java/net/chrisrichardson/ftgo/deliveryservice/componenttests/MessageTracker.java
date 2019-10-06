package net.chrisrichardson.ftgo.deliveryservice.componenttests;

import io.eventuate.tram.events.common.EventMessageHeaders;
import io.eventuate.tram.messaging.common.Message;
import io.eventuate.tram.messaging.consumer.MessageConsumer;
import io.eventuate.util.test.async.Eventually;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Predicate;

import static org.junit.Assert.fail;

public class MessageTracker {

  private Set<String> channels;

  private LinkedBlockingQueue<Message> messages = new LinkedBlockingQueue<>();

  public MessageTracker(Set<String> channels, MessageConsumer messageConsumer) {
    this.channels = channels;
    messageConsumer.subscribe("MessageTracker-messages-" + System.currentTimeMillis(), channels, this::handleMessage);
  }

  private void handleMessage(Message message) {
    messages.add(message);
  }

  private void validateChannel(String commandChannel) {
    if (!channels.contains(commandChannel))
      throw new IllegalArgumentException(String.format("%s is not one of the specified channels: %s", commandChannel, channels));
  }

  public void reset() {
    messages.clear();
  }

  private List<Message> getMessages() {
    return Arrays.asList(this.messages.toArray(new Message[this.messages.size()]));
  }

  public Message assertDomainEventPublished(String channel, Predicate<Message> test) {
    validateChannel(channel);
    Predicate<Message> mp = ((Predicate<Message>) m -> m.hasHeader(EventMessageHeaders.EVENT_TYPE)).and(test);
    Eventually.eventually(() -> {
      List<Message> messages = getMessages();
      if (messages.stream().noneMatch(mp))
        fail(String.format("Cannot find matching domain eventmessage in %s", messages));
    });
    return getMessages().stream().filter(mp).findFirst().get();
  }


}
