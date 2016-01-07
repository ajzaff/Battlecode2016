package team137.collect;

import team137.ai.actions.Action;
import team137.ai.actions.priority.Priority;

import java.util.*;

public class PrioritySet {

  private final Set<Action> minimalSet; /* set of default priorities (disjoint with index) */
  private final Map<Action, Entry> priorityIndex; /* index of (non-default) priorities (index = set) */
  private final TreeSet<Entry> prioritySet; /* ordered set of top priorities (set = index) */

  protected PrioritySet(int initialCapacity) {
    minimalSet = new HashSet<>();
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
      return cmp != 0? cmp : hashCode() < o.hashCode()? 1 :
          hashCode() > o.hashCode()? -1 : 0;
    }
    @Override
    public String toString() {
      return key + "=" + String.format("%.2f", value);
    }
  }

  protected Set<Action> getMinimalSet() {
    return minimalSet;
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
    Entry indexEntry = getPriorityIndex().get(action);
    boolean defaultEntry = getMinimalSet().contains(action);
    if(priority > Priority.LOWEST_PRIORITY.value) { /* real priorities */
      if(defaultEntry) {
        getMinimalSet().remove(action);
      }
      if(indexEntry != null) {
        getPrioritySet().remove(indexEntry);
        indexEntry.setValue(priority);
        getPrioritySet().add(indexEntry);
      }
      else {
        indexEntry = new Entry(action, priority);
        getPriorityIndex().put(action, indexEntry);
        getPrioritySet().add(indexEntry);
      }
    }
    else if(priority > Priority.FORBID_PRIORITY.value) { /* lowest possible value */
      if(indexEntry != null) {
        getPriorityIndex().remove(action);
        getPrioritySet().remove(indexEntry);
      }
      if(! defaultEntry) {
        getMinimalSet().add(action);
      }
    }
    else {
      if(indexEntry != null) { /* forbidden */
        getPriorityIndex().remove(action);
        getPrioritySet().remove(indexEntry);
      }
      if(defaultEntry) {
        getMinimalSet().remove(action);
      }
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

  protected Entry peek(Random rand) {
    if(! getPrioritySet().isEmpty()) {
      return getPrioritySet().last();
    }
    if(! getMinimalSet().isEmpty()) {
      int index = rand.nextInt(getMinimalSet().size());
      Action action = getMinimalSet().stream().skip(index).findFirst().get();
      return new Entry(action, Priority.LOWEST_PRIORITY.value);
    }
    return new Entry(null, Priority.FORBID_PRIORITY.value);
  }

  public String toString(int n) {
    int size = getPrioritySet().size();
    n = Math.min(n, size);
    return "(" + size + ") " +
        Arrays.toString(Arrays.copyOfRange(getPrioritySet()
            .descendingSet().toArray(), 0, n));
  }

  @Override
  public String toString() {
    return toString(getPrioritySet().size());
  }

}