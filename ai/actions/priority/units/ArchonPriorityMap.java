package team137.ai.actions.priority.units;

import battlecode.common.Direction;
import team137.ai.actions.archon.ActivateAction;
import team137.ai.actions.archon.ClearAction;
import team137.ai.actions.priority.Priority;

public class ArchonPriorityMap extends DefaultPriorityMap {

  public ArchonPriorityMap() {
    super(16);
    init();
  }

  @Override
  protected void init() {
    super.init();

    // 8 clear rubble actions
    putPriority(ClearAction.NORTH, Priority.FORBID_PRIORITY);
    putPriority(ClearAction.NORTH_EAST, Priority.FORBID_PRIORITY);
    putPriority(ClearAction.EAST, Priority.FORBID_PRIORITY);
    putPriority(ClearAction.SOUTH_EAST, Priority.FORBID_PRIORITY);
    putPriority(ClearAction.SOUTH, Priority.FORBID_PRIORITY);
    putPriority(ClearAction.SOUTH_WEST, Priority.FORBID_PRIORITY);
    putPriority(ClearAction.WEST, Priority.FORBID_PRIORITY);
    putPriority(ClearAction.NORTH_WEST, Priority.FORBID_PRIORITY);

    // 8 activate neutral actions
    putPriority(ActivateAction.NORTH, Priority.FORBID_PRIORITY);
    putPriority(ActivateAction.NORTH_EAST, Priority.FORBID_PRIORITY);
    putPriority(ActivateAction.EAST, Priority.FORBID_PRIORITY);
    putPriority(ActivateAction.SOUTH_EAST, Priority.FORBID_PRIORITY);
    putPriority(ActivateAction.SOUTH, Priority.FORBID_PRIORITY);
    putPriority(ActivateAction.SOUTH_WEST, Priority.FORBID_PRIORITY);
    putPriority(ActivateAction.WEST, Priority.FORBID_PRIORITY);
    putPriority(ActivateAction.NORTH_WEST, Priority.FORBID_PRIORITY);
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