package team137.collect;

import battlecode.common.GameActionException;
import battlecode.common.RobotController;
import team137.ai.actions.Action;
import team137.ai.actions.priority.Priority;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class UnitPrioritySet extends PrioritySet {
  protected UnitPrioritySet(int initialCapacity) {
    super(initialCapacity);
  }

  public void update() {

    Map<Action, Double> minimalDecayMap = new HashMap<>();

    // decay all actions!
    for(Action action : getPriorityIndex().keySet()) {
      Entry indexEntry = getPriorityIndex().get(action);
      double decayValue = Priority.decay(indexEntry.getValue());
      if(decayValue > Priority.LOWEST_PRIORITY.value) { /* normal optimized decay */
        indexEntry.setValue(decayValue);
      }
      else { /* became minimal */
        minimalDecayMap.put(action, decayValue);
      }
    }

    for(Map.Entry<Action, Double> entry : minimalDecayMap.entrySet()) {
      putPriority(entry.getKey(), entry.getValue());
    }
  }

  public Action fairAct(RobotController rc, Random rand) throws GameActionException {

    // iterate through the ordered set
    // to determine the best possible
    // course of action...

    for(Action action : this) {
      if(action.act(rc)) {

        // calculate new priority "decay" for fairness
        // return the chosen action.

        putIfLower(action, /*Priority.decay(getPriority(action))*/Priority.FORBID_PRIORITY);
        return action;
      }
    }

    // no action was permitted, or possible now-
    // Return null.

    return null;
  }
}
