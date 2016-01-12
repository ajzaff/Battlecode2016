package team137.ai.units;

import battlecode.common.MapLocation;
import battlecode.common.RobotInfo;
import battlecode.common.Team;
import team137.ai.actions.priority.Priority;
import team137.ai.tables.robots.RobotWeights;

public final class AttackUnits {

  public static MapLocation findWeakest(
      RobotInfo[] robots,
      RobotWeights attackTable,
      MapLocation curLoc,
      Priority baseAggro,
      Team team)
  {
    double bestScore = 0;
    MapLocation bestLoc = null;
    for(RobotInfo r : robots) {

      if(r.team != team && r.team != Team.NEUTRAL) {
        double dist = curLoc.distanceSquaredTo(r.location);
        double priority = attackTable.get(r.type) * baseAggro.value;
        double weakness = r.maxHealth - r.health;
        if(dist > 0) {
          double score = (priority * weakness) / dist;
          if(score > bestScore || bestLoc == null) {
            bestLoc = r.location;
            bestScore = score;
          }
        }
      }
    }
    return bestLoc;
  }
}
