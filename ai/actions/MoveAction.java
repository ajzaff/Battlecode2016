package team137.ai.actions;

import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.RobotController;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public final class MoveAction extends BaseAction {

  private static final Map<Direction, MoveAction> actionMap;

  static {
    actionMap = new HashMap<>(10);
    actionMap.put(Direction.NONE, new MoveAction(Direction.OMNI, "."));
    actionMap.put(Direction.OMNI, new MoveAction(Direction.OMNI, "."));
    actionMap.put(Direction.NORTH, new MoveAction(Direction.NORTH, "^"));
    actionMap.put(Direction.NORTH_EAST, new MoveAction(Direction.NORTH_EAST, "/^"));
    actionMap.put(Direction.EAST, new MoveAction(Direction.EAST, ">"));
    actionMap.put(Direction.SOUTH_EAST, new MoveAction(Direction.SOUTH_EAST, "\\v"));
    actionMap.put(Direction.SOUTH, new MoveAction(Direction.SOUTH, "v"));
    actionMap.put(Direction.SOUTH_WEST, new MoveAction(Direction.SOUTH_WEST, "v/"));
    actionMap.put(Direction.WEST, new MoveAction(Direction.WEST, "<"));
    actionMap.put(Direction.NORTH_WEST, new MoveAction(Direction.NORTH_WEST, "^\\"));
  }

  private static final Random RAND = new Random(1337);

  private final Direction dir;

  private MoveAction(Direction dir, String repr) {
    super(repr);
    this.dir = dir;
  }

  @Override
  public boolean act(RobotController rc) throws GameActionException {
    if(rc.canMove(dir)) {
      rc.move(dir);
      return true;
    }
    /* dir is blocked */
    Direction[] d;
    boolean choice = RAND.nextBoolean();
    if (choice) {
      d = new Direction[]{dir.rotateLeft(), dir.rotateRight()};
    } else {
      d = new Direction[]{dir.rotateRight(), dir.rotateLeft()};
    }
    if (rc.canMove(d[0])) {
      rc.move(d[0]);
      return true;
    }
    if(rc.canMove(d[1])) {
      rc.move(d[1]);
      return true;
    }
    /* left and right are blocked */
    if (choice) {
      d = new Direction[]{dir.rotateLeft().rotateLeft(), dir.rotateRight().rotateRight()};
    } else {
      d = new Direction[]{dir.rotateRight().rotateRight(), dir.rotateLeft().rotateLeft()};
    }
    if (rc.canMove(d[0])) {
      rc.move(d[0]);
      return true;
    }
    if(rc.canMove(d[1])) {
      rc.move(d[1]);
      return true;
    }
    /* movement is blocked in forward directions */
    return false;
  }

  public static MoveAction fromDirection(Direction dir) {
    return actionMap.get(dir);
  }
}
