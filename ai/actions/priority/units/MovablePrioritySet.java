package team137.ai.actions.priority.units;

import battlecode.common.Direction;
import team137.ai.actions.MoveAction;
import team137.ai.actions.priority.Priority;
import team137.ai.tables.Directions;

public class MovablePrioritySet extends BasePrioritySet {
  public MovablePrioritySet() {
  }

  protected void init() {
  }

  public void clearMotion() {
    for(Direction dir : Directions.cardinals()) {
      putIfLower(MoveAction.inDirection(dir), Priority.LOWEST_PRIORITY);
    }
  }

  public void forbidMove() {
    for(Direction dir : Directions.cardinals()) {
      putIfLower(MoveAction.inDirection(dir), Priority.FORBID_PRIORITY);
    }
  }
}
