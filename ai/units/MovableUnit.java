package team137.ai.units;

import battlecode.common.*;
import team137.ai.actions.MoveAction;
import team137.ai.actions.priority.Priority;
import team137.ai.tables.Bounds;
import team137.ai.tables.robots.FleeWeights;
import team137.collect.PrioritySet;

import java.util.Map;

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
      prioritySet.putPriority(MoveAction.inDirection(curDir), Priority.DEFAULT_PRIORITY);
    }
  }

  public void checkFlee(
      FleeWeights fleeWeights,
      MapLocation curLoc,
      RobotInfo robotInfo,
      Priority basePriority,
      Map<Direction, Double> fleeBuffer)
      throws GameActionException
  {
    if(robotInfo.team == Team.ZOMBIE || robotInfo.team.opponent() == team) {
      Direction dirToLoc = curLoc.directionTo(robotInfo.location);
      double x0 = fleeWeights.get(robotInfo.type).x0;
      double x1 = fleeWeights.get(robotInfo.type).x1;
      // forward
      fleeBuffer.put(
          dirToLoc,
          fleeBuffer.getOrDefault(dirToLoc, 0d) +
              x0 * basePriority.value);
      // left spill
      fleeBuffer.put(
          dirToLoc.rotateLeft(),
          fleeBuffer.getOrDefault(dirToLoc, 0d) +
              x1 * basePriority.value);
      // right spill
      fleeBuffer.put(
          dirToLoc.rotateRight(),
          fleeBuffer.getOrDefault(dirToLoc, 0d) +
              x1 * basePriority.value);

      // backward
      Direction backward = dirToLoc.opposite();
      fleeBuffer.put(
          backward,
          fleeBuffer.getOrDefault(dirToLoc, 0d) +
              -x0 * basePriority.value);
      // backward left spill
      fleeBuffer.put(
          backward.rotateLeft(),
          fleeBuffer.getOrDefault(dirToLoc, 0d) +
              -x1 * basePriority.value);
      // backward right spill
      fleeBuffer.put(
          backward.rotateRight(),
          fleeBuffer.getOrDefault(dirToLoc, 0d) +
              -x1 * basePriority.value);
    }
  }
}
