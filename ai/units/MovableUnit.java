package team137.ai.units;

import battlecode.common.*;
import team137.ai.actions.MoveAction;
import team137.ai.actions.priority.Priority;
import team137.ai.tables.robots.FleeWeights;
import team137.collect.PrioritySet;

public class MovableUnit extends BaseUnit {

  private final Team team;

  public MovableUnit(RobotController rc) {
    super(rc);
    team = rc.getTeam();
  }

  public void checkEnemy(
      PrioritySet prioritySet,
      FleeWeights fleeWeights,
      MapLocation curLoc,
      RobotInfo robotInfo) throws GameActionException
  {
    if(robotInfo.team.opponent() == team) {
      Direction dirToLoc = curLoc.directionTo(robotInfo.location);
      double x0 = fleeWeights.get(robotInfo.type).x0;
      double x1 = fleeWeights.get(robotInfo.type).x1;
      // forward
      prioritySet.addPriorityButPermit(
          MoveAction.fromDirection(dirToLoc),
          x0 * Priority.LEVEL16_PRIORITY.value);
      // left spill
      prioritySet.addPriorityButPermit(
          MoveAction.fromDirection(dirToLoc.rotateLeft()),
          x1 * Priority.LEVEL16_PRIORITY.value);
      // right spill
      prioritySet.addPriorityButPermit(
          MoveAction.fromDirection(dirToLoc.rotateRight()),
          x1 * Priority.LEVEL16_PRIORITY.value);

      // add priority to all other directions!
      Direction endDir = dirToLoc.rotateRight();
      dirToLoc = dirToLoc.rotateLeft();
      while((dirToLoc = dirToLoc.rotateLeft()) != endDir) {
        // add good direction
        prioritySet.addPriority(
            MoveAction.fromDirection(dirToLoc),
            -x1 * Priority.LEVEL4_PRIORITY.value);
      }

      rc.setIndicatorString(1, "See dangerous " + robotInfo.type + "; x0="+x0 + "; x1="+x1);
    }
  }
}
