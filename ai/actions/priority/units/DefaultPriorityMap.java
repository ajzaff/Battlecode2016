package team137.ai.actions.priority.units;

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

  public void fairAct(RobotController rc, Random rand) {
    List<Action> choices = new ArrayList<>();
    double bestPriority = peek().getValue();

    // select all equal likely possibilities
    for(Map.Entry<Action, Double> entry : getPriorityList()) {
      if(entry.getValue() < bestPriority) {
        break;
      }
      choices.add(entry.getKey());
    }

    Action action = choices.get(rand.nextInt(choices.size()));
    action.act(rc);

    // calculate new priority
    double newPriority = decay(action);
    putPriority(action, newPriority);
  }

  @Override
  public void update() {
    // decay all actions
    for(Action action : getPriorityMap().keySet()) {
      double decay = decay(action);
      putPriority(action, decay);
    }
  }

  protected double decay(Action action) {
    return Math.max(Priority.LOWEST_PRIORITY.value, .9 * getPriority(action) - .1);
  }
}
