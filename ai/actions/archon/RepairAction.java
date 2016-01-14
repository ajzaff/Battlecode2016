package team137.ai.actions.archon;

import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.RobotController;
import team137.ai.actions.BaseAction;
import team137.ai.tables.Directions;

public class RepairAction extends BaseAction {

  private static final RepairAction[] ACTIONS;

  static {
    ACTIONS = new RepairAction[8];
    for(Direction dir : Directions.cardinals()) {
      ACTIONS[dir.ordinal()] =
          new RepairAction(dir);
    }
  }

  private final Direction dir;

  protected RepairAction(Direction dir) {
    super("r[" + dir + "]");
    this.dir = dir;
  }

  @Override
  public boolean act(RobotController rc) throws GameActionException {
    rc.repair(rc.getLocation().add(dir));
    return true;
  }
}
