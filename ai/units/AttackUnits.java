package team137.ai.units;

import battlecode.common.MapLocation;
import battlecode.common.RobotInfo;

public final class AttackUnits {

  public static MapLocation findWeakest(RobotInfo[] robots){
    double weakestSoFar = 0;
    MapLocation weakestLocation = null;
    for(RobotInfo r : robots){
      double weakness = r.maxHealth-r.health;
      if(weakness > weakestSoFar){
        weakestLocation = r.location;
        weakestSoFar=weakness;
      }
    }
    return weakestLocation;
  }
}
