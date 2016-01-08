package team137.ai.actions.priority.units;

import team137.ai.actions.Action;
import team137.ai.actions.AttackAction;
import team137.ai.actions.priority.Priority;

import java.util.ArrayList;
import java.util.List;

public class TurretPrioritySet extends BasePrioritySet {
  public void clearAttacks() {
    List<Action> removeList = new ArrayList<Action>();
    for(Action action : getPriorityIndex().keySet()) {
      if(action instanceof AttackAction) {
        removeList.add(action);
      }
    }
    for(Action action : removeList) {
      putPriority(action, Priority.FORBID_PRIORITY);
    }
  }
}
