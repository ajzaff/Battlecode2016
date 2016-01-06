package team137.ai.actions.priority;

public interface PriorityMap<E> {
  void putPriority(E e, double priority);
  Double getPriority(E e);
  void update();
}
