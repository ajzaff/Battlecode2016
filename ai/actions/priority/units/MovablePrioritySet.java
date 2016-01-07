package team137.ai.actions.priority.units;

import battlecode.common.Direction;
import team137.ai.actions.MoveAction;
import team137.ai.actions.priority.Priority;

public class MovablePrioritySet extends BasePrioritySet {
  public MovablePrioritySet() {
    init();
  }
  protected void init() {
    for(Direction dir : Direction.values()) {
      putPriority(MoveAction.fromDirection(dir), Priority.LOWEST_PRIORITY);
    }
  }
}
