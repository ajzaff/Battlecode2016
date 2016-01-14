package team137.ai.actions.priority.units;

import team137.ai.actions.Action;
import team137.ai.actions.AttackAction;
import team137.ai.actions.priority.Priority;

public class GuardPrioritySet extends MovablePrioritySet {

  @Override
  public void init() {

  }

  public void forbidAttack() {
    for(Action action : this) {
      if(action instanceof AttackAction) {
        putPriority(action, Priority.FORBID_PRIORITY);
      }
    }
  }
}
