package team137.ai.actions;

import battlecode.common.Direction;

import battlecode.common.GameActionException;
import battlecode.common.RobotController;

import java.util.Random;

public final class MoveAction extends BaseAction {

  public static final MoveAction OMNI = new MoveAction(Direction.OMNI, ".");
  public static final MoveAction NORTH = new MoveAction(Direction.NORTH, "^");
  public static final MoveAction NORTH_EAST = new MoveAction(Direction.NORTH_EAST, "ne");
  public static final MoveAction EAST = new MoveAction(Direction.EAST, ">");
  public static final MoveAction SOUTH_EAST = new MoveAction(Direction.SOUTH_EAST, "se");
  public static final MoveAction SOUTH = new MoveAction(Direction.SOUTH, "v");
  public static final MoveAction SOUTH_WEST = new MoveAction(Direction.SOUTH_WEST, "sw");
  public static final MoveAction WEST = new MoveAction(Direction.WEST, "<");
  public static final MoveAction NORTH_WEST = new MoveAction(Direction.NORTH_WEST, "nw");

  private static final Random RAND = new Random(1337);

  private final Direction dir;
  private final String repr;

  private MoveAction(Direction dir, String repr) {
    super();
    this.dir = dir;
    this.repr = repr;
  }

  @Override
  public boolean act(RobotController rc) {
    return safeMoveProximate(rc, RAND) != Direction.OMNI;
  }

  public Direction safeMoveProximate(RobotController rc, Random rand) {
    try {
      if(rc.isCoreReady()) {
        if(rc.canMove(dir)) {
          rc.move(dir);
          return dir;
        }
        Direction d;
        if(rand.nextBoolean()) {
          d = dir.rotateLeft();
        }
        else {
          d = dir.rotateRight();
        }
        if(rc.canMove(d)) {
          rc.move(d);
          return d;
        }
      }
    }
    catch (GameActionException e) {
      e.printStackTrace();
    }
    return Direction.OMNI;
  }

  @Override
  public String toString() {
    return repr;
  }

  public static MoveAction fromDirection(Direction dir) {
    if(dir == Direction.NORTH) {
      return MoveAction.NORTH;
    }
    else if(dir == Direction.NORTH_EAST) {
      return MoveAction.NORTH_EAST;
    }
    else if(dir == Direction.EAST) {
      return MoveAction.EAST;
    }
    else if(dir == Direction.SOUTH_EAST) {
      return MoveAction.SOUTH_EAST;
    }
    else if(dir == Direction.SOUTH) {
      return MoveAction.SOUTH;
    }
    else if(dir == Direction.SOUTH_WEST) {
      return MoveAction.SOUTH_WEST;
    }
    else if(dir == Direction.WEST) {
      return MoveAction.WEST;
    }
    else if(dir == Direction.NORTH_WEST) {
      return MoveAction.NORTH_WEST;
    }
    else {
      return MoveAction.OMNI;
    }
  }

//  public boolean safeMove(RobotController rc) {
//    try {
//      if(rc.isCoreReady() && rc.canMove(dir)) {
//        rc.move(dir);
//        return true;
//      }
//    }
//    catch (GameActionException e) {
//      e.printStackTrace();
//    }
//    return false;
//  }
}
