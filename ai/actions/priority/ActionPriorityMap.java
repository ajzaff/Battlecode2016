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

  private final Map<Action, Map.Entry<Action, Double>> priorityIndex;
  private final PriorityQueue<Map.Entry<Action, Double>> priorityQueue;

  protected ActionPriorityMap(int initialCapacity) {
    priorityIndex = new HashMap<>(initialCapacity);
    priorityQueue = new PriorityQueue<>(initialCapacity, getComparator());
  }

  public int size() {
    return getPriorityIndex().size();
  }

  public boolean isEmpty() {
    return size() == 0;
  }

  protected Map<Action, Map.Entry<Action, Double>> getPriorityIndex() {
    return priorityIndex;
  }

  protected PriorityQueue<Map.Entry<Action, Double>> getPriorityQueue() {
    return priorityQueue;
  }

  @Override
  public Double getPriority(Action action) {
    return getPriorityIndex().get(action).getValue();
  }

  @Override
  public void putPriority(Action action, double priority) {
    if(getPriorityIndex().containsKey(action)) {
      Map.Entry<Action, Double> entry =
          getPriorityIndex().get(action);
      getPriorityQueue().remove(entry);
      entry.setValue(priority);
      getPriorityQueue().add(entry);
    }
    else {
      Map.Entry<Action, Double> entry =
          new HashMap.SimpleEntry<>(action, priority);
      getPriorityQueue().add(entry);
      getPriorityIndex().put(action, entry);
    }
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

  protected Map.Entry<Action, Double> peek() {
    return getPriorityQueue().peek();
  }

  public static Comparator<Map.Entry<Action, Double>> getComparator() {
    return comparator;
  }

  public String toString(int n) {
    return "(" + getPriorityQueue().size() + ") " +
        Arrays.toString(Arrays.copyOfRange(
            getPriorityQueue().toArray(), 0, n));
  }

  @Override
  public String toString() {
    return toString(size());
  }

}