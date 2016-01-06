package team137.ai.actions.priority.units;

import team137.ai.actions.archon.ClearAction;
import team137.ai.actions.priority.Priority;

public class ArchonPriorityMap extends DefaultPriorityMap {
  public ArchonPriorityMap() {
    super(18);
    init();
  }

  @Override
  protected void init() {
    super.init();

    // 9 clear rubble actions
    putPriority(ClearAction.OMNI, Priority.DEFAULT_PRIORITY);
    putPriority(ClearAction.NORTH, Priority.DEFAULT_PRIORITY);
    putPriority(ClearAction.NORTH_EAST, Priority.DEFAULT_PRIORITY);
    putPriority(ClearAction.EAST, Priority.DEFAULT_PRIORITY);
    putPriority(ClearAction.SOUTH_EAST, Priority.DEFAULT_PRIORITY);
    putPriority(ClearAction.SOUTH, Priority.DEFAULT_PRIORITY);
    putPriority(ClearAction.SOUTH_WEST, Priority.DEFAULT_PRIORITY);
    putPriority(ClearAction.WEST, Priority.DEFAULT_PRIORITY);
    putPriority(ClearAction.NORTH_WEST, Priority.DEFAULT_PRIORITY);
  }
}