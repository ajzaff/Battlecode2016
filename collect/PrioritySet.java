package team137.collect;

import team137.ai.actions.Action;
import team137.ai.actions.priority.Priority;

import java.util.*;

public class PrioritySet implements Iterable<Action> {

  private final Set<Action> minimalSet; /* set of default priorities (disjoint with index) */
  private final Map<Action, Entry> priorityIndex; /* index of (non-default) priorities (index = set) */
  private final TreeSet<Entry> prioritySet; /* ordered set of top priorities (set = index) */

  protected PrioritySet(int initialCapacity) {
    minimalSet = new HashSet<>();
    priorityIndex = new HashMap<>(initialCapacity);
    prioritySet = new TreeSet<>();
  }

  class SetIterator implements Iterator<Action> {

    private final Iterator<Entry> prioritySetIterator;
    private final Iterator<Action> minimalSetIterator;

    SetIterator() {
      prioritySetIterator = prioritySet.descendingIterator();
      // TODO: make random iterator for minimal set (prevents predictable behavior)
      minimalSetIterator = minimalSet.iterator();
    }

    @Override
    public boolean hasNext() {
      return prioritySetIterator.hasNext() || minimalSetIterator.hasNext();
    }

    @Override
    public Action next() throws NoSuchElementException {
      if(prioritySetIterator.hasNext()) {
        return prioritySetIterator.next().getKey();
      }
      if(minimalSetIterator.hasNext()) {
        return minimalSetIterator.next();
      }
      throw new NoSuchElementException();
    }

    @Override
    public void remove() {
      // not implemented.
    }
  }

  public static class Entry implements Comparable<Entry> {

    private static final Entry FORBID_ENTRY =
        new Entry(null, Priority.FORBID_PRIORITY.value);

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
      return getValue().compareTo(o.getValue());
    }
    @Override
    public String toString() {
      return key + "=" + String.format("%.2f", value);
    }
    public static Entry getForbidEntry() {
      return FORBID_ENTRY;
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
    if(getMinimalSet().contains(action)) {
      return Priority.LOWEST_PRIORITY.value;
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
    else { /* forbidden */
      if(indexEntry != null) {
        getPriorityIndex().remove(action);
        getPrioritySet().remove(indexEntry);
      }
      if(defaultEntry) {
        getMinimalSet().remove(action);
      }
    }
  }

  public void putPriorityButPermit(Action action, Priority priority) {
    putPriority(action, Math.max(priority.value, Priority.LOWEST_PRIORITY.value));
  }

  public void putPriority(Action action, Priority priority) {
    putPriority(action, priority.value);
  }

  public void addPriority(Action action, Priority priority) {
    addPriority(action, priority.value);
  }

  public void addPriority(Action action, double priority) {
    if(priority != 0) {
      putPriority(action, getPriority(action) + priority);
    }
  }

  public void addPriorityButPermit(Action action, double priority) {
    if(priority != 0) {
      putPriority(action, Math.max(getPriority(action) + priority, Priority.LOWEST_PRIORITY.value));
    }
  }

  public void putIfHigher(Action action, Priority priority) {
    if(priority.value > getPriority(action)) {
      putPriority(action, priority);
    }
  }

  public void putIfLower(Action action, Priority priority) {
    putIfLower(action, priority.value);
  }

  public void putIfLower(Action action, double priority) {
    if(priority < getPriority(action)) {
      putPriority(action, priority);
    }
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
    return Entry.getForbidEntry();
  }

  @Override
  public Iterator<Action> iterator() {
    return new SetIterator();
  }

  public String toString(int n) {
    int size = getPrioritySet().size();
    n = Math.min(n, size);
    StringBuilder sb = new StringBuilder("brain: " + n);
    int i = 0;
    for(Action action : this) {
      sb.append(' ');
      sb.append(action);
      sb.append('=');
      sb.append(getPriority(action));
      sb.append(';');
      if(i++ >= n)
        break;
    }
    return sb.toString();
  }

  @Override
  public String toString() {
    return toString(getPrioritySet().size());
  }

}