package team137.ai.tables;

import battlecode.common.*;
import battlecode.common.Direction;
import battlecode.common.MapLocation;

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

  public Direction update(RobotController rc, MapLocation curLoc, int sensorRadius) throws GameActionException {
    MapLocation loc = new MapLocation(curLoc.x, curLoc.y);
    Direction d;
    d = checkBound(rc, Type.NORTH, Direction.NORTH, curLoc, sensorRadius); loc = loc.add(d);
    d = checkBound(rc, Type.EAST, Direction.EAST, curLoc, sensorRadius);   loc = loc.add(d);
    d = checkBound(rc, Type.SOUTH, Direction.SOUTH, curLoc, sensorRadius); loc = loc.add(d);
    d = checkBound(rc, Type.WEST, Direction.WEST, curLoc, sensorRadius);   loc = loc.add(d);
    return curLoc.directionTo(loc);
  }

  private Direction checkBound(
      RobotController rc,
      Type type,
      Direction dir,
      MapLocation curLoc,
      int range) throws GameActionException
  {
    MapLocation loc = curLoc.add(dir, range);
    if(rc.onTheMap(loc)) {
      if(noBound(type)) {
        bounds[type.ordinal()] = loc.y;
        return dir;
      }
    }
    return Direction.OMNI;
  }

  private boolean noBound(Type type) {
    return bounds[type.ordinal()] != NO_BOUND;
  }

  public static Bounds newBounds() {
    return new Bounds();
  }
}
