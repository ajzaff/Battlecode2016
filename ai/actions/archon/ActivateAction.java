package team137.ai.actions.archon;

import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import battlecode.common.RobotController;
import team137.ai.actions.BaseAction;

public class ActivateAction extends BaseAction {

  public static final ActivateAction NORTH = new ActivateAction(Direction.NORTH);
  public static final ActivateAction NORTH_EAST = new ActivateAction(Direction.NORTH_EAST);
  public static final ActivateAction EAST = new ActivateAction(Direction.EAST);
  public static final ActivateAction SOUTH_EAST = new ActivateAction(Direction.SOUTH_EAST);
  public static final ActivateAction SOUTH = new ActivateAction(Direction.SOUTH);
  public static final ActivateAction SOUTH_WEST = new ActivateAction(Direction.SOUTH_WEST);
  public static final ActivateAction WEST = new ActivateAction(Direction.WEST);
  public static final ActivateAction NORTH_WEST = new ActivateAction(Direction.NORTH_WEST);

  private final Direction dir;

  private ActivateAction(Direction dir) {
    this.dir = dir;
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
}
