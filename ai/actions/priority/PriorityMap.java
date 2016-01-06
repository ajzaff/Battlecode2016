package team137.ai.actions.priority;

import battlecode.common.RobotController;
import team137.ai.actions.Action;

public interface PriorityMap<E> {
  void putPriority(E e, double priority);
  Double getPriority(E e);
  void update();
}
