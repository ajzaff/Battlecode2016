package team137;

import battlecode.common.RobotController;
import team137.ai.units.*;
import team137.ai.units.Guard;

@SuppressWarnings("unused")
public final class RobotPlayer {
  public static void run(RobotController rc) {
    Unit unit;
    switch(rc.getType()) {
      case ARCHON:
        unit = new Archon(rc);
        break;
      case SOLDIER:
        unit = new Soldier(rc);
        break;
      case GUARD:
        unit = new Guard(rc);
        break;
      case SCOUT:
        unit = new Scout(rc);
        break;
      case VIPER:
        unit = new Viper(rc);
        break;
      case TTM:
        unit = new TTM(rc);
        break;
      case TURRET:
        unit = new Turret(rc);
        break;
      default:
        throw new IllegalStateException(
            "unhandled robot type: " + rc.getType().name());
    }
    unit.run();
  }
}