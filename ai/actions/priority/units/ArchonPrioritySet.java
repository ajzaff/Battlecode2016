package team137.ai.actions.priority.units;

import battlecode.common.Direction;
import team137.ai.actions.archon.ActivateAction;
import team137.ai.actions.priority.Priority;

public class ArchonPrioritySet extends MovablePrioritySet {

  public ArchonPrioritySet() {
    super();
    init();
  }

  public void addActivatePriority(Direction dir, Priority priority) {
    if(dir != Direction.OMNI && dir != Direction.NONE) {
      addPriority(ActivateAction.fromDirection(dir), priority);
    }
  }

  public void clearActivatePriority(Direction dir) {
    if(dir != Direction.OMNI && dir != Direction.NONE) {
      putPriority(ActivateAction.fromDirection(dir), Priority.LOWEST_PRIORITY);
    }
  }

  public void clearActivatePriority() {
    for(Direction dir : Direction.values()) {
      clearActivatePriority(dir);
    }
  }

  public void forbidActivate(Direction dir) {
    if(dir != Direction.OMNI && dir != Direction.NONE) {
      putPriority(ActivateAction.fromDirection(dir), Priority.FORBID_PRIORITY);
    }
  }

  public void forbidActivate() {
    for(Direction dir : Direction.values()) {
      forbidActivate(dir);
    }
  }
}