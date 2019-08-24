package net.chrisrichardson.ftgo.restaurantservice.events;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.List;

public class RestaurantMenuDTO {
  private List<MenuItemDTO> menuItems;

  private RestaurantMenuDTO() {
  }

  @Override
  public boolean equals(Object o) {
    return EqualsBuilder.reflectionEquals(this, o);
  }

  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this);
  }

  public List<MenuItemDTO> getMenuItemDTOs() {
    return menuItems;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }

  public void setMenuItemDTOs(List<MenuItemDTO> menuItems) {
    this.menuItems = menuItems;
  }

  public RestaurantMenuDTO(List<MenuItemDTO> menuItems) {

    this.menuItems = menuItems;
  }

}