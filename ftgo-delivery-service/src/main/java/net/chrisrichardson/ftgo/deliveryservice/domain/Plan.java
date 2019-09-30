package net.chrisrichardson.ftgo.deliveryservice.domain;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.JoinColumn;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Plan {

  @ElementCollection
  @CollectionTable(name = "courier_actions", joinColumns = @JoinColumn(name = "courier_id"))
  private List<Action> actions = new LinkedList<>();

  public void add(Action action) {
    actions.add(action);
  }

  public void removeDelivery(Delivery order) {
    actions = actions.stream().filter(action -> !action.actionFor(order)).collect(Collectors.toList());
  }

  public List<Action> getActions() {
    return actions;
  }

  public List<Action> actionsForDelivery(Delivery order) {
    return actions.stream().filter(action -> action.actionFor(order)).collect(Collectors.toList());
  }
}
