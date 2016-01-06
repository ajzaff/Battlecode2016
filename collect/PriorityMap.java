package team137.collect;

import battlecode.common.Direction;
import team137.ai.actions.Action;
import team137.ai.actions.MoveAction;
import team137.ai.actions.priority.Priority;

import java.util.*;

public abstract class PriorityMap {

  private final Map<Action, Entry> priorityIndex;
  private final TreeSet<Entry> prioritySet;

  protected PriorityMap(int initialCapacity) {
    priorityIndex = new HashMap<>(initialCapacity);
    prioritySet = new TreeSet<>();
  }

  public static class Entry implements Comparable<Entry> {
    private final Action key;
    private Double value;
    public Entry(Action key, Double value) {
      this.key = key;
      this.value = value;
    }
    public Action getKey() {
      return key;
    }
    public Double getValue() {
      return value;
    }
    public void setValue(double value) {
      this.value = value;
    }
    @Override
    public int compareTo(Entry o) {
      int cmp = getValue().compareTo(o.getValue());
      if(cmp == 0) {
        cmp = hashCode() < o.hashCode()? 1 :
            hashCode() > o.hashCode()? -1 : 0;
      }
      return cmp;
    }
    @Override
    public String toString() {
      return key + "=" + String.format("%.2f", value);
    }
  }

  public int size() {
    return getPrioritySet().size();
  }

  public boolean isEmpty() {
    return size() == 0;
  }

  protected TreeSet<Entry> getPrioritySet() {
    return prioritySet;
  }

  protected Map<Action, Entry> getPriorityIndex() {
    return priorityIndex;
  }

  public Double getPriority(Action action) {
    if(getPriorityIndex().containsKey(action)) {
      return getPriorityIndex().get(action).getValue();
    }
    return Priority.FORBID_PRIORITY.value;
  }

  public void putPriority(Action action, double priority) {
    Entry entry = getPriorityIndex().get(action);
    if(entry == null) {
      entry = new Entry(action, priority);
      getPriorityIndex().put(action, entry);
      getPrioritySet().add(entry);
    }
    else {
      entry = getPriorityIndex().get(action);
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

  protected Entry peek() {
    return getPrioritySet().last();
  }

  public String toString(int n) {
    n = Math.min(n, size());
    return "(" + size() + ") " +
        Arrays.toString(Arrays.copyOfRange(getPrioritySet()
            .descendingSet().toArray(), 0, n));
  }

  @Override
  public String toString() {
    return toString(size());
  }

}