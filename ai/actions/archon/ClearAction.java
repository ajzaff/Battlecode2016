package team137.ai.actions.archon;

import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import battlecode.common.RobotController;
import team137.ai.actions.BaseAction;

public final class ClearAction extends BaseAction {

  public static final ClearAction OMNI = new ClearAction(Direction.OMNI);
  public static final ClearAction NORTH = new ClearAction(Direction.NORTH);
  public static final ClearAction NORTH_EAST = new ClearAction(Direction.NORTH_EAST);
  public static final ClearAction EAST = new ClearAction(Direction.EAST);
  public static final ClearAction SOUTH_EAST = new ClearAction(Direction.SOUTH_EAST);
  public static final ClearAction SOUTH = new ClearAction(Direction.SOUTH);
  public static final ClearAction SOUTH_WEST = new ClearAction(Direction.SOUTH_WEST);
  public static final ClearAction WEST = new ClearAction(Direction.WEST);
  public static final ClearAction NORTH_WEST = new ClearAction(Direction.NORTH_WEST);

  private final Direction dir;

  private ClearAction(Direction dir) {
    this.dir = dir;
  }

  @Override
  public boolean act(RobotController rc) {
    return safeClearRubble(rc);
  }

  public boolean safeClearRubble(RobotController rc) {
    try {
      if(rc.isCoreReady() && rc.onTheMap(rc.getLocation().add(dir))) {
        rc.clearRubble(dir);
        return true;
      }
    }
    catch (GameActionException e) {
      e.printStackTrace();
    }
    return false;
  }
}
