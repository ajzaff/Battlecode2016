package team137.collect;

import battlecode.common.*;
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

  public void fairAct(RobotController rc, Random rand) throws GameActionException {
    Entry bestEntry = peek(rand);
    Action action = bestEntry.getKey();

    if(action != null) {
      action.act(rc);

      // calculate new priority "decay" for fairness
      if(bestEntry.getValue() > Priority.LOWEST_PRIORITY.value) {
        putPriority(action, Priority.decay(getPriority(action)));
      }
    }
  }
}
