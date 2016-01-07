package team137.ai.actions.archon;

import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import battlecode.common.RobotController;
import team137.ai.actions.BaseAction;

import java.util.HashMap;
import java.util.Map;

public class ActivateAction extends BaseAction {

  private static final Map<Direction, ActivateAction> actionMap;

  static {
    actionMap = new HashMap<>(8);
    actionMap.put(Direction.NORTH, new ActivateAction(Direction.NORTH, "+n"));
    actionMap.put(Direction.NORTH_EAST, new ActivateAction(Direction.NORTH, "+ne"));
    actionMap.put(Direction.EAST, new ActivateAction(Direction.NORTH, "+e"));
    actionMap.put(Direction.SOUTH_EAST, new ActivateAction(Direction.NORTH, "+se"));
    actionMap.put(Direction.SOUTH, new ActivateAction(Direction.NORTH, "+s"));
    actionMap.put(Direction.SOUTH_WEST, new ActivateAction(Direction.NORTH, "+sw"));
    actionMap.put(Direction.WEST, new ActivateAction(Direction.NORTH, "+w"));
    actionMap.put(Direction.NORTH_WEST, new ActivateAction(Direction.NORTH, "+nw"));
  }

  private final Direction dir;

  private ActivateAction(Direction dir, String repr) {
    super(repr);
    this.dir = dir;
  }

  @Override
  public boolean act(RobotController rc) throws GameActionException {
    MapLocation loc = rc.getLocation().add(dir);
    rc.activate(loc);
    return true;
  }

  public static ActivateAction fromDirection(Direction dir) {
    return actionMap.get(dir);
  }
}
