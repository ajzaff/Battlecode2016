package team137.ai.actions.archon;

import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.RobotController;
import team137.ai.actions.BaseAction;

public final class ClearAction extends BaseAction {

  public static final ClearAction NORTH = new ClearAction(Direction.NORTH, "Xn");
  public static final ClearAction NORTH_EAST = new ClearAction(Direction.NORTH_EAST, "Xne");
  public static final ClearAction EAST = new ClearAction(Direction.EAST, "Xe");
  public static final ClearAction SOUTH_EAST = new ClearAction(Direction.SOUTH_EAST, "Xse");
  public static final ClearAction SOUTH = new ClearAction(Direction.SOUTH, "Xs");
  public static final ClearAction SOUTH_WEST = new ClearAction(Direction.SOUTH_WEST, "Xsw");
  public static final ClearAction WEST = new ClearAction(Direction.WEST, "Xw");
  public static final ClearAction NORTH_WEST = new ClearAction(Direction.NORTH_WEST, "Xnw");

  private final Direction dir;

  private ClearAction(Direction dir, String repr) {
    super(repr);
    this.dir = dir;
    this.repr = repr;
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

  public static ClearAction fromDirection(Direction dir) {
    if(dir == Direction.NORTH) {
      return ClearAction.NORTH;
    }
    else if(dir == Direction.NORTH_EAST) {
      return ClearAction.NORTH_EAST;
    }
    else if(dir == Direction.EAST) {
      return ClearAction.EAST;
    }
    else if(dir == Direction.SOUTH_EAST) {
      return ClearAction.SOUTH_EAST;
    }
    else if(dir == Direction.SOUTH) {
      return ClearAction.SOUTH;
    }
    else if(dir == Direction.SOUTH_WEST) {
      return ClearAction.SOUTH_WEST;
    }
    else if(dir == Direction.WEST) {
      return ClearAction.WEST;
    }
    else if(dir == Direction.NORTH_WEST) {
      return ClearAction.NORTH_WEST;
    }
    return null;
  }
}
