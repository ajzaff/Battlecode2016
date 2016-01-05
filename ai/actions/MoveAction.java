package team137.ai.actions;

import battlecode.common.Direction;

import battlecode.common.GameActionException;
import battlecode.common.RobotController;

import java.util.Random;

public final class MoveAction extends BaseAction {

  public static final MoveAction NORTH = new MoveAction(Direction.NORTH);
  public static final MoveAction NORTH_EAST = new MoveAction(Direction.NORTH_EAST);
  public static final MoveAction EAST = new MoveAction(Direction.EAST);
  public static final MoveAction SOUTH_EAST = new MoveAction(Direction.SOUTH_EAST);
  public static final MoveAction SOUTH = new MoveAction(Direction.SOUTH);
  public static final MoveAction SOUTH_WEST = new MoveAction(Direction.SOUTH_WEST);
  public static final MoveAction WEST = new MoveAction(Direction.WEST);
  public static final MoveAction NORTH_WEST = new MoveAction(Direction.NORTH_WEST);

  private static final Random RAND = new Random(1337);

  private Direction dir;

  private MoveAction(Direction dir) {
    super();
    this.dir = dir;
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

  public boolean safeMove(RobotController rc) {
    try {
      if(rc.isCoreReady() && rc.canMove(dir)) {
        rc.move(dir);
        return true;
      }
    }
    catch (GameActionException e) {
      e.printStackTrace();
    }
    return false;
  }
}
