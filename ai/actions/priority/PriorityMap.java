package team137.ai.actions.priority;

import battlecode.common.RobotController;
import team137.ai.actions.Action;

public interface PriorityMap {
  double putPriority(Action action, double priority);
  double getPriority(Action action);
  boolean act(RobotController rc);
  void update();
}
