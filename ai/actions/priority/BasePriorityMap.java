package team137.ai.actions.priority;

import battlecode.common.RobotController;
import team137.ai.actions.Action;

import java.util.*;

public abstract class BasePriorityMap implements PriorityMap {

  private final Map<Action, Double> priorityMap;
  private final List<Map.Entry<Action, Double>> priorityList;

  protected BasePriorityMap(int initialCapacity) {
    priorityMap = new HashMap<>(initialCapacity);
    priorityList = new ArrayList<>(initialCapacity);
  }

  protected BasePriorityMap() {
    priorityMap = new HashMap<>();
    priorityList = new ArrayList<>();
  }

  public int size() {
    return getPriorityMap().size();
  }

  public boolean isEmpty() {
    return size() == 0;
  }

  protected Map<Action, Double> getPriorityMap() {
    return priorityMap;
  }

  protected List<Map.Entry<Action, Double>> getPriorityList() {
    return priorityList;
  }

  @Override
  public double putPriority(Action action, double priority) {
    double oldValue = getPriorityMap().put(action, priority);
    resort();
    return oldValue;
  }

  public double putPriority(Action action, Priority priority) {
    return putPriority(action, priority.value);
  }

  @Override
  public double getPriority(Action action) {
    return getPriorityMap().get(action);
  }

  @Override
  public boolean act(RobotController rc) {
    return peek().getKey().act(rc);
  }

  protected Map.Entry<Action, Double> peek() {
    if(isEmpty()) {
      return null;
    }
    return getPriorityList().get(0);
  }

  protected void resort() {
    getPriorityList().sort(Collections.reverseOrder());
  }
}