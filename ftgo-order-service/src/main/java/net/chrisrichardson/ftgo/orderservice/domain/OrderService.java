package net.chrisrichardson.ftgo.orderservice.domain;

import io.micrometer.core.instrument.MeterRegistry;
import net.chrisrichardson.ftgo.common.Restaurant;
import net.chrisrichardson.ftgo.common.RestaurantRepository;
import net.chrisrichardson.ftgo.consumerservice.domain.ConsumerService;
import net.chrisrichardson.ftgo.orderservice.api.events.OrderDetails;
import net.chrisrichardson.ftgo.orderservice.api.events.OrderLineItem;
import net.chrisrichardson.ftgo.orderservice.web.MenuItemIdAndQuantity;
import net.chrisrichardson.ftgo.common.MenuItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

import static java.util.stream.Collectors.toList;

@Transactional
public class OrderService {

  private Logger logger = LoggerFactory.getLogger(getClass());

  private OrderRepository orderRepository;

  private RestaurantRepository restaurantRepository;

  private Optional<MeterRegistry> meterRegistry;

  private ConsumerService consumerService;

  public OrderService(OrderRepository orderRepository,
                      RestaurantRepository restaurantRepository,
                      Optional<MeterRegistry> meterRegistry,
                      ConsumerService consumerService) {

    this.orderRepository = orderRepository;
    this.restaurantRepository = restaurantRepository;
    this.meterRegistry = meterRegistry;
    this.consumerService = consumerService;
  }

  @Transactional
  public Order createOrder(long consumerId, long restaurantId,
                           List<MenuItemIdAndQuantity> lineItems) {
    Restaurant restaurant = restaurantRepository.findById(restaurantId)
            .orElseThrow(() -> new RestaurantNotFoundException(restaurantId));

    List<OrderLineItem> orderLineItems = makeOrderLineItems(lineItems, restaurant);

    Order order = new Order(consumerId, restaurant, orderLineItems);
    orderRepository.save(order);

    OrderDetails orderDetails = new OrderDetails(consumerId, restaurantId, orderLineItems, order.getOrderTotal());

    consumerService.validateOrderForConsumer(consumerId, orderDetails.getOrderTotal());
    confirmCreateTicket(order.getId());
    approveOrder(order.getId());

    meterRegistry.ifPresent(mr -> mr.counter("placed_orders").increment());

    return order;
  }

  private List<OrderLineItem> makeOrderLineItems(List<MenuItemIdAndQuantity> lineItems, Restaurant restaurant) {
    return lineItems.stream().map(li -> {
      MenuItem om = restaurant.findMenuItem(li.getMenuItemId()).orElseThrow(() -> new InvalidMenuItemIdException(li.getMenuItemId()));
      return new OrderLineItem(li.getMenuItemId(), om.getName(), om.getPrice(), li.getQuantity());
    }).collect(toList());
  }

  @Transactional
  public Order cancel(Long orderId) {
    Order order = tryToFindOrder(orderId);

    beginCancel(orderId);
    cancelTicket(order.getRestaurant().getId(), orderId);
    confirmCancelTicket(order.getRestaurant().getId(), orderId);
    confirmCancelled(orderId);

    return order;
  }

  private Order updateOrder(long orderId, Consumer<Order> updater) {
    return orderRepository.findById(orderId).map(order -> {
      updater.accept(order);
      return order;
    }).orElseThrow(() -> new OrderNotFoundException(orderId));
  }

  public void approveOrder(long orderId) {
    updateOrder(orderId, Order::noteApproved);
    meterRegistry.ifPresent(mr -> mr.counter("approved_orders").increment());
  }

  public void beginCancel(long orderId) {
    updateOrder(orderId, Order::cancel);
  }

  public void confirmCancelled(long orderId) {
    updateOrder(orderId, Order::noteCancelled);
  }

  @Transactional
  public Order reviseOrder(long orderId, OrderRevision orderRevision) {
    Order order = tryToFindOrder(orderId);
    Optional<RevisedOrder> revisedOrder = beginReviseOrder(orderId, orderRevision);
    revisedOrder.ifPresent(ro -> {
      beginReviseTicket(order.getRestaurant().getId(), orderId, orderRevision.getRevisedLineItemQuantities());
      confirmReviseTicket(order.getRestaurant().getId(), orderId, orderRevision.getRevisedLineItemQuantities());
      confirmRevision(orderId, orderRevision);
    });
    return order;
  }

  public Optional<RevisedOrder> beginReviseOrder(long orderId, OrderRevision revision) {
    return orderRepository.findById(orderId).map(order -> new RevisedOrder(order, order.revise(revision)));
  }

  public void confirmRevision(long orderId, OrderRevision revision) {
    updateOrder(orderId, order -> order.confirmRevision(revision));
  }

  public void confirmCreateTicket(Long orderId) {
    tryToFindOrder(orderId).confirmCreateTicket();
  }

  public void cancelCreateTicket(Long orderId) {
    tryToFindOrder(orderId).cancelCreateTicket();
  }

  public void acceptTicket(long orderId, LocalDateTime readyBy) {
    tryToFindOrder(orderId).acceptTicket(readyBy);
  }

  private void cancelTicket(long restaurantId, long orderId) {
    tryToFindOrder(orderId).cancelTicket();
  }

  public void confirmCancelTicket(long restaurantId, long orderId) {
    tryToFindOrder(orderId).confirmCancelTicket();
  }

  public void undoCancelTicket(long restaurantId, long orderId) {
    tryToFindOrder(orderId).undoCancelTicket();
  }

  public void beginReviseTicket(long restaurantId, Long orderId, Map<String, Integer> revisedLineItemQuantities) {
    tryToFindOrder(orderId).beginReviseTicket(revisedLineItemQuantities);
  }

  public void undoBeginReviseTicket(long restaurantId, Long orderId) {
    tryToFindOrder(orderId).undoBeginReviseTicket();
  }

  public void confirmReviseTicket(long restaurantId, long orderId, Map<String, Integer> revisedLineItemQuantities) {
    tryToFindOrder(orderId).confirmReviseTicket(revisedLineItemQuantities);
  }

  private Order tryToFindOrder(Long orderId) {
    return orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException(orderId));
  }
}
