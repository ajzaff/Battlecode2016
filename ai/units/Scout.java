package team137.ai.units;

import static battlecode.common.RobotType.*;

import battlecode.common.*;
import team137.ai.actions.MoveAction;
import team137.ai.actions.priority.Priority;
import team137.ai.actions.priority.units.ScoutPrioritySet;
import team137.ai.tables.Directions;
import team137.ai.tables.robots.FleeWeights;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Scout extends MovableUnit {

  /// "SHARED"

  private static final int SCOUT_RADIUS = (int) Math.sqrt(SCOUT.sensorRadiusSquared);
  private static final FleeWeights FLEE_WEIGHTS = FleeWeights.newInstance();

  static {
    FLEE_WEIGHTS.put(ZOMBIEDEN, FleeWeights.Row.getNull());
    FLEE_WEIGHTS.put(ARCHON, FleeWeights.Row.getNull());
  }

  /// INSTANCE

  private final Team team;
  private final Random rand;
  private final ScoutPrioritySet prioritySet;
  private Direction dir;

  public Scout(RobotController rc) {
    super(rc);
    team = rc.getTeam();
    rand = new Random(rc.getID());
    prioritySet = new ScoutPrioritySet();
  }

  @Override
  public void update() {

    MapLocation curLoc = rc.getLocation();

    try {

      if(rc.isCoreReady()) {

        // Initial direction
        if (dir == null) {
          senseWall(curLoc);
        }

        // add scout motion!

        prioritySet.putPriority(MoveAction.inDirection(dir), Priority.DEFAULT_PRIORITY);

        RobotInfo[] localRobots = rc.senseHostileRobots(curLoc, SCOUT.sensorRadiusSquared);
        Map<Direction, Double> fleeBuffer = new HashMap<>(8);

        for (RobotInfo robot : localRobots) {
          checkFlee(
              FLEE_WEIGHTS,
              curLoc,
              robot,
              .1,
              fleeBuffer
          );
        }

        applyFleeBuffer(fleeBuffer, prioritySet);

        prioritySet.fairAct(rc, rand);
      }

      rc.setIndicatorString(0, prioritySet.toString());
    }
    catch (GameActionException e) {
      e.printStackTrace();
    }
  }

  private void senseWall(MapLocation curLoc) throws GameActionException {
    Direction wallDir = null;
    for(Direction dir : Directions.cardinals()) {
      MapLocation locFromCur = curLoc.add(dir, 4);
      if(! rc.onTheMap(locFromCur)) {
        wallDir = dir;
        break;
      }
    }
    if(wallDir == null) {
      dir = Directions.cardinals()[rand.nextInt(8)];
    } else {
      dir = wallDir.opposite();
    }
  }
}
