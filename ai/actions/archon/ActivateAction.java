package team137.ai.actions.archon;

import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import battlecode.common.RobotController;
import team137.ai.actions.BaseAction;

public class ActivateAction extends BaseAction {

  public static final ActivateAction NORTH = new ActivateAction(Direction.NORTH, "!n");
  public static final ActivateAction NORTH_EAST = new ActivateAction(Direction.NORTH_EAST, "!ne");
  public static final ActivateAction EAST = new ActivateAction(Direction.EAST, "!e");
  public static final ActivateAction SOUTH_EAST = new ActivateAction(Direction.SOUTH_EAST, "!se");
  public static final ActivateAction SOUTH = new ActivateAction(Direction.SOUTH, "!s");
  public static final ActivateAction SOUTH_WEST = new ActivateAction(Direction.SOUTH_WEST, "!sw");
  public static final ActivateAction WEST = new ActivateAction(Direction.WEST, "!w");
  public static final ActivateAction NORTH_WEST = new ActivateAction(Direction.NORTH_WEST, "!nw");

  private final Direction dir;
  private final String repr;

  private ActivateAction(Direction dir, String repr) {
    this.dir = dir;
    this.repr = repr;
  }

  @Override
  public boolean act(RobotController rc) {
    return safeActivate(rc);
  }

  public boolean safeActivate(RobotController rc) {
    try {
      if(rc.isCoreReady()) {
        MapLocation loc = rc.getLocation().add(dir);
        rc.activate(loc);
        return true;
      }
    }
    catch(GameActionException e) {
      e.printStackTrace();
    }
    return false;
  }

  @Override
  public String toString() {
    return repr;
  }
}
