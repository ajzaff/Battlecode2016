package team137.ai.units;

import battlecode.common.*;
import team137.ai.actions.Action;
import team137.ai.actions.MoveAction;
import team137.ai.actions.archon.ActivateAction;
import team137.ai.actions.priority.Priority;
import team137.ai.actions.priority.units.ArchonPrioritySet;
import team137.ai.tables.Bounds;
import team137.ai.tables.Rubble;
import team137.ai.tables.robots.FleeWeights;
import team137.ai.tables.robots.RobotTable;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static battlecode.common.RobotType.ARCHON;
import static battlecode.common.RobotType.ZOMBIEDEN;

public class Archon extends MovableUnit {

  ///////////// "SHARED" FIELDS

  public static final FleeWeights FLEE_TABLE = FleeWeights.newInstance();


  static {
    // CUSTOMIZE FLEE_TABLE
    FLEE_TABLE.put(ZOMBIEDEN, FleeWeights.Row.getNull());
  }

  private static final double[] RUBBLE_MAP = Rubble.defaultMap();
  private static final int SENSOR_RADIUS = (int) Math.sqrt(ARCHON.sensorRadiusSquared);

  ///////////// ROBOT CONSTANT FIELDS

  private final ArchonPrioritySet prioritySet; // priority-queue-like structure
  private final Random rand;                   // random number generator

  ///////////// OPERATION FIELDS

  private final Bounds bounds;
  private Direction curDir;

  public Archon(RobotController rc) {
    super(rc);
    prioritySet = new ArchonPrioritySet();
    bounds = Bounds.newBounds();
    rand = new Random(rc.getID());
  }

  @Override
  public void update() {

    // TURN VARIABLES

    MapLocation curLoc = rc.getLocation();
//    MapLocation[] localTiles = getAllMapLocationsWithinRadiusSq
//        (curLoc, ARCHON.sensorRadiusSquared);               // local tiles
//    MapLocation[] adjacentTiles = getAllMapLocationsWithinRadiusSq
//        (curLoc, 2);                                        // adjacent tiles
    Map<Direction, Double> rubbleDirMap = new HashMap<>(8); // adjacent rubble multipliers (for path-finding)
    boolean adjacentNeutral = false;                        // Activation flag

    // START TURN

    try {

      avoidWalls(curLoc);

      RobotInfo[] localRobots = rc.senseNearbyRobots();

      // do stuff with local robots!
      for(RobotInfo robotInfo : localRobots) {
        adjacentNeutral |= checkNeutrals(curLoc, robotInfo);
        checkEnemy(prioritySet, FLEE_TABLE, curLoc, robotInfo);
      }

      // BEGIN ACTUATION

      rc.setIndicatorString(0, prioritySet.toString(7));  // debug
      if(adjacentNeutral) {
        prioritySet.clearMotion();
      }
      else {
        prioritySet.forbidActivate();                     // apply neutral fence
      }
//      applyRubbleMap(rubbleDirMap);                       // apply rubble map!
      if(rc.isCoreReady()) {
        prioritySet.fairAct(rc, rand);                    // act!

        // DECAY PRIORITIES

        prioritySet.update();                             // update priority map
      }

    }
    catch (GameActionException e) {
      e.printStackTrace();
    }
  }

  private void avoidWalls(MapLocation curLoc) throws GameActionException {
    Direction d = bounds.update(rc, curLoc, SENSOR_RADIUS);
    if(d != Direction.OMNI && d != Direction.NONE) {
      curDir = d;
    }
    if(curDir != null) {
      prioritySet.putPriority(MoveAction.fromDirection(curDir), Priority.DEFAULT_PRIORITY);
    }
  }

//  private void applyRubbleMap(Map<Direction, Double> rubbleDirMap) {
//    for(Direction dir : rubbleDirMap.keySet()) {
//      Action action = MoveAction.fromDirection(dir);
//      double oldValue = prioritySet.getPriority(action);
//      if(oldValue > Priority.FORBID_PRIORITY.value) {
//        double rubble = rubbleDirMap.get(dir);
//        double newValue = Rubble.weight(RUBBLE_MAP, rubble) * oldValue;
//        if(newValue != oldValue) {
//          prioritySet.putPriority(MoveAction.fromDirection(dir), newValue);
//        }
//      }
//    }
//  }

//  public void checkRubble(Map<Direction, Double> rubbleDirMap, MapLocation[] adjacentTiles, MapLocation curLoc) {
//    for(MapLocation loc : adjacentTiles) {
//      double rubble = rc.senseRubble(loc);
//      Direction dir = curLoc.directionTo(loc);
//      rubbleDirMap.put(dir, Rubble.weight(RUBBLE_MAP, rubble));
//    }
//  }

  public boolean checkNeutrals(MapLocation curLoc, RobotInfo robotInfo) throws GameActionException {
    // returns flag for ``adjacentNeutral``
    boolean adjacentNeutral = false;
    if(robotInfo.team == Team.NEUTRAL) {
      Direction dirToLoc = curLoc.directionTo(robotInfo.location);
      Action action;
      Priority priority;
      if (curLoc.isAdjacentTo(robotInfo.location)) {
        action = ActivateAction.fromDirection(dirToLoc);
        priority = Priority.LEVEL8_PRIORITY;
        adjacentNeutral = true;
      } else {
        action = MoveAction.fromDirection(dirToLoc);
        priority = Priority.DEFAULT_PRIORITY;
      }
      prioritySet.addPriority(action, priority);
    }
    return adjacentNeutral;
  }


}
