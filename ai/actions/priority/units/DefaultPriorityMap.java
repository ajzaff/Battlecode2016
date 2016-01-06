package team137.ai.actions.priority.units;

import battlecode.common.Direction;
import battlecode.common.RobotController;
import team137.ai.actions.Action;
import team137.ai.actions.MoveAction;
import team137.collect.PriorityMap;
import team137.ai.actions.priority.Priority;

import java.util.*;

public class DefaultPriorityMap extends PriorityMap {

  protected DefaultPriorityMap(int requiredCapacity) {
    super(9 + requiredCapacity);
    init();
  }

  protected void init() {
    // put 9 movement actions.
    putPriority(MoveAction.OMNI, Priority.DEFAULT_PRIORITY);
    putPriority(MoveAction.NORTH, Priority.DEFAULT_PRIORITY);
    putPriority(MoveAction.NORTH_EAST, Priority.DEFAULT_PRIORITY);
    putPriority(MoveAction.EAST, Priority.DEFAULT_PRIORITY);
    putPriority(MoveAction.SOUTH_EAST, Priority.DEFAULT_PRIORITY);
    putPriority(MoveAction.SOUTH, Priority.DEFAULT_PRIORITY);
    putPriority(MoveAction.SOUTH_WEST, Priority.DEFAULT_PRIORITY);
    putPriority(MoveAction.WEST, Priority.DEFAULT_PRIORITY);
    putPriority(MoveAction.NORTH_WEST, Priority.DEFAULT_PRIORITY);
  }

  public void addMovePriority(Direction dir, Priority priority) {
    addPriority(MoveAction.fromDirection(dir), priority);
  }

  public void clearMovePriority(Direction dir) {
    putPriority(MoveAction.fromDirection(dir), Priority.LOWEST_PRIORITY);
  }

  public void forbidMovePriority(Direction dir) {
    putPriority(MoveAction.fromDirection(dir), Priority.FORBID_PRIORITY);
  }

  public void forbidMove() {
    for(Direction dir : Direction.values()) {
      forbidMovePriority(dir);
    }
  }

  public void clearMovePriority() {
    for(Direction dir : Direction.values()) {
      clearMovePriority(dir);
    }
  }

  public void fairAct(RobotController rc) {
    Action action = peek().getKey();
    action.act(rc);

    // calculate new priority "decay" for fairness
    putPriority(action, decay(action));
  }

  public void update() {
    // decay all actions!
    // ------------------------------------------------
    // We can access the index directly for decay.
    // Due to the assumption of a linear decay function
    // the order in the priority queue will not change.
    // ------------------------------------------------
    for(Action action : getPriorityIndex().keySet()) {
      Entry entry = getPriorityIndex().get(action);
      entry.setValue(decay(entry.getValue()));
    }
  }

  protected double decay(Action action) {
    return decay(getPriority(action));
  }

  protected double decay(double priority) {
    // important: decay must be linear!
    return Math.max(Priority.LOWEST_PRIORITY.value, .9 * priority - .1);
  }
}
