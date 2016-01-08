package team137.ai.tables;

import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import battlecode.common.RobotController;

public class Bounds {

  public static final int NO_BOUND = Integer.MIN_VALUE;

  public enum Type {
    NORTH,
    EAST,
    SOUTH,
    WEST
  }

  private int[] bounds;

  private Bounds() {
    bounds = new int[4];
    for(int i=0; i < 4; i++) {
      bounds[i] = Integer.MAX_VALUE;
    }
  }

  public void update(RobotController rc, MapLocation curLoc, int range) throws GameActionException {
    checkBound(rc, Type.NORTH, Direction.NORTH, curLoc, range);
    checkBound(rc, Type.EAST, Direction.EAST, curLoc, range);
    checkBound(rc, Type.SOUTH, Direction.SOUTH, curLoc, range);
    checkBound(rc, Type.WEST, Direction.WEST, curLoc, range);
  }

  private void checkBound(RobotController rc,
                          Type type,
                          Direction dir,
                          MapLocation curLoc,
                          int range)
      throws GameActionException
  {
    if(noBound(type)) {
      MapLocation loc = curLoc.add(dir, range);
      if (rc.onTheMap(loc)) {
        bounds[type.ordinal()] = loc.y;
      }
    }
  }

  private boolean noBound(Type type) {
    return bounds[type.ordinal()] != NO_BOUND;
  }

  public static Bounds newBounds() {
    return new Bounds();
  }
}
