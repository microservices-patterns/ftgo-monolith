package net.chrisrichardson.ftgo.orderservice;

import net.chrisrichardson.ftgo.common.Money;
import net.chrisrichardson.ftgo.domain.*;
import net.chrisrichardson.ftgo.orderservice.web.MenuItemIdAndQuantity;

import java.util.Collections;
import java.util.List;

import static net.chrisrichardson.ftgo.orderservice.RestaurantMother.*;

public class OrderDetailsMother {

  public static long CONSUMER_ID = 1511300065921L;

  public static final int CHICKEN_VINDALOO_QUANTITY = 5;
  public static final MenuItemIdAndQuantity CHICKEN_VINDALOO_MENU_ITEM_AND_QUANTITY = new MenuItemIdAndQuantity(RestaurantMother.CHICKEN_VINDALOO_MENU_ITEM_ID, CHICKEN_VINDALOO_QUANTITY);
  public static final List<MenuItemIdAndQuantity> CHICKEN_VINDALOO_MENU_ITEMS_AND_QUANTITIES = Collections.singletonList(CHICKEN_VINDALOO_MENU_ITEM_AND_QUANTITY);

  public static List<OrderLineItem> chickenVindalooLineItems() {
    return Collections.singletonList(new OrderLineItem(CHICKEN_VINDALOO_MENU_ITEM_AND_QUANTITY.getMenuItemId(),
            CHICKEN_VINDALOO,
            CHICKEN_VINDALOO_PRICE,
            CHICKEN_VINDALOO_MENU_ITEM_AND_QUANTITY.getQuantity()));
  }

  public static final Money CHICKEN_VINDALOO_ORDER_TOTAL = CHICKEN_VINDALOO_PRICE.multiply(5);

  public static long ORDER_ID = 99L;

  public static Order CHICKEN_VINDALOO_ORDER = makeAjantaOrder();

  public static final OrderState CHICKEN_VINDALOO_ORDER_STATE = OrderState.APPROVED;

  private static Order makeAjantaOrder() {
    Order order = new Order(CONSUMER_ID, new Restaurant(AJANTA_ID, "", new RestaurantMenu(Collections.emptyList())), chickenVindalooLineItems());
    order.setId(ORDER_ID);
    return order;
  }
}
