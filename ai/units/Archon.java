package team137.ai.units;

import static battlecode.common.RobotType.*;

import battlecode.common.*;
import team137.ai.actions.Action;
import team137.ai.actions.MoveAction;
import team137.ai.actions.archon.ActivateAction;
import team137.ai.actions.priority.Priority;
import team137.ai.actions.priority.units.ArchonPrioritySet;
import team137.ai.tables.Bounds;
import team137.ai.tables.robots.DefaultFleeWeights;
import team137.ai.tables.robots.FleeWeights;
import team137.ai.tables.robots.RobotTable;
import team137.ai.tables.Rubble;

import java.util.*;

public class Archon extends BaseUnit {

  ///////////// "SHARED" FIELDS

  public static final RobotTable<FleeWeights.Row> FLEE_TABLE = FleeWeights.newInstance();


  static {
    // CUSTOMIZE FLEE_TABLE
    FLEE_TABLE.put(ZOMBIEDEN, FleeWeights.Row.getNull());
  }

  private static final double[] RUBBLE_MAP = Rubble.defaultMap();
  private static final int SENSOR_RADIUS = (int) Math.sqrt(ARCHON.sensorRadiusSquared);

  ///////////// ROBOT CONSTANT FIELDS

  private final ArchonPrioritySet prioritySet; // priority-queue-like structure
  private final Random rand;                   // random number generator
  private final Team team;                     // our team

  ///////////// OPERATION FIELDS

  private final Bounds bounds;
  private Direction curDir;

  public Archon(RobotController rc) {
    super(rc);
    prioritySet = new ArchonPrioritySet();
    bounds = Bounds.newBounds();
    team = rc.getTeam();
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
        checkEnemy(curLoc, robotInfo);
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

  public void checkEnemy(
      MapLocation curLoc,
      RobotInfo robotInfo) throws GameActionException
  {
    if(robotInfo.team != team && robotInfo.team != Team.NEUTRAL) {
      Direction dirToLoc = curLoc.directionTo(robotInfo.location);
      double x0 = FLEE_TABLE.get(robotInfo.type).x0;
      double x1 = FLEE_TABLE.get(robotInfo.type).x1;
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
