package team137.ai.actions.priority.units;

import team137.ai.actions.Action;
import team137.ai.actions.MoveAction;
import team137.ai.actions.PassAction;
import team137.ai.actions.priority.BasePriorityMap;
import team137.ai.actions.priority.Priority;

public class UnitPriorityMap extends BasePriorityMap {

  protected UnitPriorityMap(int initialCapacity) {
    super(initialCapacity);
    init();
  }

  protected UnitPriorityMap() {
    super();
    init();
  }

  protected void init() {
    // put 8 movement actions.
    putPriority(PassAction.INSTANCE, Priority.DEFAULT_PRIORITY);
    putPriority(MoveAction.NORTH, Priority.DEFAULT_PRIORITY);
    putPriority(MoveAction.NORTH_EAST, Priority.DEFAULT_PRIORITY);
    putPriority(MoveAction.EAST, Priority.DEFAULT_PRIORITY);
    putPriority(MoveAction.SOUTH_EAST, Priority.DEFAULT_PRIORITY);
    putPriority(MoveAction.SOUTH, Priority.DEFAULT_PRIORITY);
    putPriority(MoveAction.SOUTH_WEST, Priority.DEFAULT_PRIORITY);
    putPriority(MoveAction.WEST, Priority.DEFAULT_PRIORITY);
    putPriority(MoveAction.NORTH_WEST, Priority.DEFAULT_PRIORITY);
  }

  @Override
  public void update() {
    for(Action action : getPriorityMap().keySet()) {
      double decay = Math.max(.9 * getPriorityMap().get(action) - .1, 0);
      getPriorityMap().put(action, decay);
    }
  }
}
