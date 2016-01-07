package team137.ai.actions.archon;

import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.RobotController;
import team137.ai.actions.BaseAction;

import java.util.HashMap;
import java.util.Map;

public final class ClearAction extends BaseAction {

  private static final Map<Direction, ClearAction> actionMap;

  static {
    actionMap = new HashMap<>(8);
    actionMap.put(Direction.NORTH, new ClearAction(Direction.NORTH, "Xn"));
    actionMap.put(Direction.NORTH_EAST, new ClearAction(Direction.NORTH, "Xne"));
    actionMap.put(Direction.EAST, new ClearAction(Direction.NORTH, "Xe"));
    actionMap.put(Direction.SOUTH_EAST, new ClearAction(Direction.NORTH, "Xse"));
    actionMap.put(Direction.SOUTH, new ClearAction(Direction.NORTH, "Xs"));
    actionMap.put(Direction.SOUTH_WEST, new ClearAction(Direction.NORTH, "Xsw"));
    actionMap.put(Direction.WEST, new ClearAction(Direction.NORTH, "Xw"));
    actionMap.put(Direction.NORTH_WEST, new ClearAction(Direction.NORTH, "Xnw"));
  }

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
    return actionMap.get(dir);
  }
}
