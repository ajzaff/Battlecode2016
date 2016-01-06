package team137.collect;

import team137.ai.actions.Action;
import team137.ai.actions.priority.Priority;

import java.util.*;

public abstract class PriorityMap {

  private static final Comparator<Map.Entry<Action, Double>> comparator =
      new Comparator<Map.Entry<Action, Double>>() {
        @Override
        public int compare(Map.Entry<Action, Double> o1, Map.Entry<Action, Double> o2) {
          return o1.getValue().compareTo(o2.getValue());
        }
      };

  private final Map<Action, Map.Entry<Action, Double>> priorityIndex;
  private final TreeSet<Map.Entry<Action, Double>> prioritySet;

  protected PriorityMap(int initialCapacity) {
    priorityIndex = new HashMap<>(initialCapacity);
    prioritySet = new TreeSet<>(getComparator());
  }

  public int size() {
    return getPrioritySet().size();
  }

  public boolean isEmpty() {
    return size() == 0;
  }

  protected TreeSet<Map.Entry<Action, Double>> getPrioritySet() {
    return prioritySet;
  }

  protected Map<Action, Map.Entry<Action, Double>> getPriorityIndex() {
    return priorityIndex;
  }

  public Double getPriority(Action action) {
    if(getPriorityIndex().containsKey(action)) {
      return getPriorityIndex().get(action).getValue();
    }
    return Priority.FORBID_PRIORITY.value;
  }

  public void putPriority(Action action, double priority) {
    Map.Entry<Action, Double> entry = getPriorityIndex().get(action);
    if(entry == null) {
      entry = new TreeMap.SimpleEntry<>(action, priority);
      getPriorityIndex().put(action, entry);
      getPrioritySet().add(entry);
    }
    else {
      getPrioritySet().remove(entry);
      entry.setValue(priority);
      getPrioritySet().add(entry);
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
    return getPrioritySet().last();
  }

  public static Comparator<Map.Entry<Action, Double>> getComparator() {
    return comparator;
  }

  public String toString(int n) {
    n = Math.min(n, size());
    return "(" + getPrioritySet().size() + ") " +
        Arrays.toString(Arrays.copyOfRange(getPrioritySet()
            .descendingSet().toArray(), 0, n));
  }

  @Override
  public String toString() {
    return toString(size());
  }

}