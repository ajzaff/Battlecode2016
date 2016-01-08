package team137.ai.units;

import battlecode.common.*;
import team137.ai.actions.MoveAction;
import team137.ai.actions.priority.Priority;
import team137.ai.tables.Bounds;
import team137.ai.tables.robots.FleeWeights;
import team137.collect.PrioritySet;

public class MovableUnit extends BaseUnit {

  private final Team team;

  ///////////// OPERATION FIELDS

  private final Bounds bounds;
  private Direction curDir;

  public MovableUnit(RobotController rc) {
    super(rc);
    team = rc.getTeam();
    bounds = Bounds.newBounds();
  }

  public void avoidWalls(PrioritySet prioritySet, MapLocation curLoc, int sensorRadius) throws GameActionException {
    Direction d = bounds.update(rc, curLoc, sensorRadius);
    if(d != Direction.OMNI && d != Direction.NONE) {
      curDir = d;
    }
    if(curDir != null) {
      prioritySet.putPriority(MoveAction.fromDirection(curDir), Priority.DEFAULT_PRIORITY);
    }
  }

  public void checkEnemy(
      PrioritySet prioritySet,
      FleeWeights fleeWeights,
      MapLocation curLoc,
      RobotInfo robotInfo) throws GameActionException
  {c
    if(robotInfo.team == Team.ZOMBIE || robotInfo.team.opponent() == team) {
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
