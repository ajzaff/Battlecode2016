package team137.ai.actions;

import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.RobotController;
import battlecode.common.RobotType;
import team137.ai.tables.Directions;

public final class BuildAction extends BaseAction {

  private static final BuildAction[][] ACTIONS;

  static {
    ACTIONS = new BuildAction[12][8];
    for(RobotType type : RobotType.values()) {
      for(Direction dir : Directions.cardinals()) {
        ACTIONS[type.ordinal()][dir.ordinal()] =
            new BuildAction(type, dir);
      }
    }
  }

  public final Direction dir;
  public final RobotType type;

  private BuildAction(RobotType type, Direction dir) {
    super("*" + type + "[" + dir + "]");
    this.type = type;
    this.dir = dir;
  }

  public static BuildAction getInstance(RobotType type, Direction dir) {
    return ACTIONS[type.ordinal()][dir.ordinal()];
  }

  @Override
  public boolean act(RobotController rc) throws GameActionException {
    if(rc.canBuild(dir, type)) {
      rc.build(dir, type);
      return true;
    }
    return false;
  }
}
