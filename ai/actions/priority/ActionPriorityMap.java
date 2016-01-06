package team137.ai.actions.priority;

import team137.ai.actions.Action;

import java.util.*;

public abstract class ActionPriorityMap implements PriorityMap<Action> {

  private static final Comparator<Map.Entry<Action, Double>> comparator =
      new Comparator<Map.Entry<Action, Double>>() {
        @Override
        public int compare(Map.Entry<Action, Double> o1, Map.Entry<Action, Double> o2) {
          return o1.getValue() < o2.getValue()? 1 :
              o1.getValue() > o2.getValue()? -1 : 0;
        }
      };

  private final Map<Action, Double> priorityMap;
  private final List<Map.Entry<Action, Double>> priorityList;

  protected ActionPriorityMap(int initialCapacity) {
    priorityMap = new HashMap<>(initialCapacity);
    priorityList = new ArrayList<>(initialCapacity);
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
  public void putPriority(Action action, double priority) {
    getPriorityMap().put(action, priority);
    putPriorityList(action, priority);
    sort();
  }

  public void putPriority(Action action, Priority priority) {
    putPriority(action, priority.value);
  }

  public void subPriority(Action action, Priority priority) {
    putPriority(action, getPriority(action) - priority.value);
  }

  public void addPriority(Action action, Priority priority) {
    putPriority(action, getPriority(action) + priority.value);
  }

  protected void putPriorityList(Action action, double priority) {
    for(Map.Entry<Action, Double> e : getPriorityList()) {
      if(e.getKey() == action) {
        e.setValue(priority);
        return;
      }
    }
    Map.Entry<Action, Double> entry =
        new HashMap.SimpleEntry<>(action, priority);
    getPriorityList().add(entry);
  }

  @Override
  public Double getPriority(Action action) {
    return getPriorityMap().get(action);
  }

  protected Map.Entry<Action, Double> peek() {
    if(isEmpty()) {
      return null;
    }
    return getPriorityList().get(0);
  }

  protected void sort() {
    getPriorityList().sort(getComparator());
  }

  public static Comparator<Map.Entry<Action, Double>> getComparator() {
    return comparator;
  }

  @Override
  public String toString() {
    return toString(size());
  }

  public String toString(int n) {
    return "(" + getPriorityList().size() + ") " +
        Arrays.toString(getPriorityList().subList(0, n).toArray());
  }

}