package team137.ai.actions;

import battlecode.common.Direction;
import battlecode.common.RobotType;
import team137.ai.tables.Directions;

public final class BuildAction {

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
    this.type = type;
    this.dir = dir;
  }

  public static BuildAction getInstance(RobotType type, Direction dir) {
    return ACTIONS[type.ordinal()][dir.ordinal()];
  }
}
