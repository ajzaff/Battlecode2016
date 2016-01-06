package team137.ai.actions.priority.units;

import battlecode.common.Direction;
import battlecode.common.RobotController;
import team137.ai.actions.Action;
import team137.ai.actions.MoveAction;
import team137.ai.actions.priority.ActionPriorityMap;
import team137.ai.actions.priority.Priority;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class DefaultPriorityMap extends ActionPriorityMap {

  protected DefaultPriorityMap(int initialCapacity) {
    super(initialCapacity);
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

  public void fairAct(RobotController rc, Random rand) {
//    List<Action> choices = new ArrayList<>();
//    double bestPriority = peek().getValue();
//
//    // select all equal likely possibilities
//    for(Map.Entry<Action, Double> entry : getPriorityQueue()) {
//      if(entry.getValue() < bestPriority) {
//        break;
//      }
//      choices.add(entry.getKey());
//    }
//
//    Action action = choices.get(rand.nextInt(choices.size()));

    // do the best thing first!
    Action action = peek().getKey();
    action.act(rc);

    // calculate new priority "decay" for fairness
    putPriority(action, decay(action));
  }

  @Override
  public void update() {
    // decay all actions!
    // ------------------------------------------------
    // We can access the index directly for decay.
    // Due to the assumption of a linear decay function
    // the order in the priority queue will not change.
    // ------------------------------------------------
    for(Map.Entry<Action, Double> entry : getPriorityIndex().values()) {
      entry.setValue(decay(entry.getKey()));
    }
  }

  protected double decay(Action action) {
    // important: decay must be linear!
    return decay(getPriority(action));
  }

  protected double decay(double priority) {
    // important: decay must be linear!
    return Math.max(Priority.LOWEST_PRIORITY.value, .9 * priority - .1);
  }
}
