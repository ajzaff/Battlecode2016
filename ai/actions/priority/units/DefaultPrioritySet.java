package team137.ai.actions.priority.units;

import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.RobotController;
import team137.ai.actions.Action;
import team137.ai.actions.MoveAction;
import team137.collect.PrioritySet;
import team137.ai.actions.priority.Priority;

import java.util.*;

public class DefaultPrioritySet extends PrioritySet {

  protected DefaultPrioritySet() {
    super(50);
    init();
  }

  protected void init() {
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

  public void fairAct(RobotController rc, Random rand) throws GameActionException {
    Entry bestEntry = peek(rand);
    Action action = bestEntry.getKey();

    if(action != null) {
      action.act(rc);

      // calculate new priority "decay" for fairness
      if(bestEntry.getValue() > Priority.LOWEST_PRIORITY.value) {
        putPriority(action, decay(action));
      }
    }
  }

  public void update() {
    // decay all actions!
    for(Entry entry : getPrioritySet()) {
      putPriority(entry.getKey(), decay(entry.getKey()));
    }
  }

  protected double decay(Action action) {
    return decay(getPriority(action));
  }

  protected double decay(double priority) {
    // important: decay must be linear!
    return Math.max(Priority.LOWEST_PRIORITY.value, .9 * priority - .01);
  }
}
