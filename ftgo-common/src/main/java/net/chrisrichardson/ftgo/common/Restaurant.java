package net.chrisrichardson.ftgo.common;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "restaurants")
@Access(AccessType.FIELD)
public class Restaurant {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

//  @Embedded
//  private RestaurantMenu menu;

  @Embedded
  @ElementCollection
  @CollectionTable(name = "restaurant_menu_items")
  private List<MenuItem> menuItems;

  private Restaurant() {
  }

  public Restaurant(String name, RestaurantMenu menu) {
    this.name = name;
    this.menuItems = menu.getMenuItems();
  }

  public Restaurant(Long id, String name, RestaurantMenu menu) {
    this.id = id;
    this.name = name;
    this.menuItems = menu.getMenuItems();
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Long getId() {
    return id;
  }

  public Optional<MenuItem> findMenuItem(String menuItemId) {
    return menuItems.stream().filter(mi -> mi.getId().equals(menuItemId)).findFirst();
  }

  public void reviseMenu(RestaurantMenu revisedMenu) {
    throw new UnsupportedOperationException();
  }
}
